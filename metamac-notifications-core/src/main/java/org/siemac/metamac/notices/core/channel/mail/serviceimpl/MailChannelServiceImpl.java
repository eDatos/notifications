package org.siemac.metamac.notices.core.channel.mail.serviceimpl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.notices.core.constants.NoticesMessagesConstants;
import org.siemac.metamac.notices.core.navigation.InternalWebApplicationNavigation;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.core.utils.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MailChannelServiceImpl implements MailChannelService {

    @Autowired
    private JavaMailSender              mailSender;

    @Autowired
    private VelocityEngine              velocityEngine;

    @Autowired
    private NoticesConfigurationService noticesConfiguration;

    @Autowired
    private BaseDo2DtoMapper            baseDo2DtoMapper;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void sendMail(ServiceContext serviceContext, final Notice notice, final String[] mailsTo, final String replyTo) throws MetamacException {
        // Prepare email
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // Mail configuration
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setSubject(notice.getSubject());
                message.setTo(mailsTo);
                
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

                String plainText = mergeTemplateIntoString(NoticesConstants.CHANNEL_MAIL_TEMPLATE_NOTIFICATION_PLAIN, model);
                String htmlText = mergeTemplateIntoString(NoticesConstants.CHANNEL_MAIL_TEMPLATE_NOTIFICATION_HTML, model);
                message.setText(plainText, htmlText);
            }
        };

        this.mailSender.send(preparator);
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
}
