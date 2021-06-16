package org.siemac.metamac.notices.core.channel.mail.serviceimpl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.notices.core.constants.NoticesMessagesConstants;
import org.siemac.metamac.notices.core.navigation.InternalWebApplicationNavigation;
import org.siemac.metamac.notices.core.notice.domain.App;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum;
import org.siemac.metamac.notices.core.notice.serviceapi.NoticesService;
import org.siemac.metamac.notices.core.notice.serviceimpl.util.NoticesServiceUtil;
import org.siemac.metamac.notices.core.utils.TemplateUtils;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MailChannelServiceImpl implements MailChannelService {

    private final static Logger logger = LoggerFactory.getLogger(MailChannelServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private NoticesConfigurationService noticesConfiguration;

    @Autowired
    private BaseDo2DtoMapper baseDo2DtoMapper;

    @Autowired
    private NoticesService noticesService;

    @Autowired
    private NoticesConfigurationService noticesConfigurationService;

    @Override
    public Set<String> sendMail(ServiceContext serviceContext, final Notice notice, final String[] mailsTo, final String replyTo) throws MetamacException {
        Set<String> receiversWithError = new HashSet<String>();
        for (String receiver : mailsTo) {
            try {
                MimeMessagePreparator preparator = createPreparatorForReceiver(notice, replyTo, receiver);
                this.mailSender.send(preparator);
            } catch (Exception e) {
                logger.warn("Error sending notice to " + receiver, e);
                receiversWithError.add(receiver);
            }
        }
        createErrorNoticeForWrongReceivers(serviceContext, receiversWithError, notice.getSubject());
        return receiversWithError;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private MimeMessagePreparator createPreparatorForReceiver(final Notice notice, final String replyTo, String receiver) {
        return new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // Mail configuration
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setSubject(notice.getSubject());
                message.setTo(receiver);

                if (replyTo != null) {
                    message.setReplyTo(replyTo);
                }

                message.setFrom(noticesConfiguration.retrieveChannelMailUsername());

                Locale locale = noticesConfiguration.retrieveLanguageDefaultLocale();
                String organisation = noticesConfiguration.retrieveOrganisation();

                // Mail data
                Map model = new HashMap();
                model.put("organisation", organisation);
                model.put("notice", notice);
                model.put("createdDate", TemplateUtils.formatVelocityDate(notice.getCreatedDate()));
                model.put("baseDo2DtoMapper", baseDo2DtoMapper);
                model.put("messages", LocaleUtil.class);
                model.put("locale", locale);
                model.put("noticeType", getLocalisedNoticeType(notice, locale));
                model.put("noticeUrl", generateNoticeInternalApplicationUrl(notice.getUrn()));

                if (NoticeType.ANNOUNCEMENT.equals(notice.getNoticeType())) {
                    model.put("expirationDate", TemplateUtils.formatVelocityDate(notice.getExpirationDate()));
                }

                String plainText = mergeTemplateIntoString(
                        NoticesServiceUtil.isExternalUser(notice) ? NoticesConstants.CHANNEL_MAIL_TEMPLATE_EXTERNAL_USER_NOTIFICATION_PLAIN : NoticesConstants.CHANNEL_MAIL_TEMPLATE_NOTIFICATION_PLAIN,
                        model);
                String htmlText = mergeTemplateIntoString(
                        NoticesServiceUtil.isExternalUser(notice) ? NoticesConstants.CHANNEL_MAIL_TEMPLATE_EXTERNAL_USER_NOTIFICATION_HTML : NoticesConstants.CHANNEL_MAIL_TEMPLATE_NOTIFICATION_HTML,
                        model);
                message.setText(plainText, htmlText);
            }
        };
    }

    private String mergeTemplateIntoString(String template, Map<String, Object> model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
    }

    private String getLocalisedNoticeType(Notice notice, Locale locale) {
        String code = NoticeType.ANNOUNCEMENT.equals(notice.getNoticeType()) ? NoticesMessagesConstants.NOTICE_TYPE_ANNOUNCEMENT : NoticesMessagesConstants.NOTICE_TYPE_NOTIFICATION;
        return LocaleUtil.getMessageForCode(code, locale);
    }

    private String generateNoticeInternalApplicationUrl(String noticeUrn) throws MetamacException {
        String internalWebApplicationUrlBase = noticesConfiguration.retrieveNoticesInternalWebApplicationUrlBase();
        return InternalWebApplicationNavigation.buildNoticeUrl(internalWebApplicationUrlBase, noticeUrn);
    }

    private void createErrorNoticeForWrongReceivers(ServiceContext ctx, Set<String> wrongReceiversMails, String subject) throws MetamacException {

        if (wrongReceiversMails.isEmpty()) {
            return;
        }

        Locale locale = noticesConfigurationService.retrieveLanguageDefaultLocale();

        String receiversListText = StringUtils.join(wrongReceiversMails, ",");
        String localisedSubject = MessageFormat.format(LocaleUtil.getMessageForCode(NoticesMessagesConstants.NOTICE_NOT_SEND_TO_RECEIVER_SUBJECT, locale), receiversListText);
        String localisedMessage = MessageFormat.format(LocaleUtil.getMessageForCode(NoticesMessagesConstants.NOTICE_NOT_SEND_TO_RECEIVER_DESCRIPTION, locale), subject, receiversListText);

        Map<String, User> usernamesThatHaveToReceiveTheNotice = calculateUsernamesThatHaveToReceiveTheNoticeForWrongReceivers(ctx);
        for (Map.Entry<String, User> user : usernamesThatHaveToReceiveTheNotice.entrySet()) {
            if (!wrongReceiversMails.contains(user.getValue().getMail())) {
                Notice notice = new Notice(NoticesConstants.SECURITY_APPLICATION_ID, localisedSubject, NoticeType.NOTIFICATION);
                Message message = new Message(localisedMessage);
                notice.addMessage(message);
                Receiver receiverElement = new Receiver();
                receiverElement.setUsername(user.getKey());
                notice.addReceiver(receiverElement);
                noticesService.createNotice(ctx, notice);
            }
        }
    }

    /**
     * This method calculate the list of usernames of the administrators of the access control application. These are the users that have to be notified when a mail of an user is wrong.
     * 
     * @param ctx
     * @return
     * @throws MetamacException
     */
    private Map<String, User> calculateUsernamesThatHaveToReceiveTheNoticeForWrongReceivers(ServiceContext ctx) throws MetamacException {
        Notice notice = new Notice("dummy", "dummy", NoticeType.NOTIFICATION);
        Role role = new Role();
        role.setName(NoticesRoleEnum.ADMINISTRADOR.toString());
        notice.addRole(role);
        App app = new App();
        app.setName(NoticesConstants.GESTOR_ACCESOS_APPLICATION_ID);
        notice.addApp(app);
        List<User> users = noticesService.calculateReceiversOfAccessControl(ctx, notice);
        Map<String, User> usersByUsername = new HashMap<String, User>();
        for (User user : users) {
            usersByUsername.put(user.getUsername(), user);
        }
        return usersByUsername;
    }
}
