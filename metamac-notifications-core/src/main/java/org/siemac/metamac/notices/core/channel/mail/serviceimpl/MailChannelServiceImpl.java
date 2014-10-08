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
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.notices.core.constants.NoticesMailTemplateConstants;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
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
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setSubject(notice.getSubject());
                message.setTo(mailsTo);
                message.setReplyTo(replyTo);
                message.setFrom(noticesConfiguration.retrieveChannelMailUsername());

                String bundleName = "i18n/messages-notices";
                Locale locale = noticesConfiguration.retrieveLanguageDefaultLocale();

                // Mail data
                Map model = new HashMap();
                model.put("notice", notice);
                model.put("createdDate", CoreCommonUtil.transformDateTimeToISODateTimeLexicalRepresentation(notice.getCreatedDate()));
                model.put("baseDo2DtoMapper", baseDo2DtoMapper);
                model.put("messages", LocaleUtil.class);
                model.put("locale", locale);
                model.put("bundleName", bundleName);
                model.put("noticeType", getLocalisedNoticeType(notice, bundleName, locale));

                if (NoticeType.ANNOUNCEMENT.equals(notice.getNoticeType())) {
                    model.put("expirationDate", CoreCommonUtil.transformDateTimeToISODateTimeLexicalRepresentation(notice.getExpirationDate()));
                }

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, NoticesConstants.CHANNEL_MAIL_TEMPLATE_NOTIFICATION_PLAIN, "UTF-8", model);
                message.setText(text, false); // Text Plain
            }
        };

        this.mailSender.send(preparator);
    }

    private String getLocalisedNoticeType(Notice notice, String bundleName, Locale locale) {
        String code = NoticeType.ANNOUNCEMENT.equals(notice.getNoticeType()) ? NoticesMailTemplateConstants.NOTICE_TYPE_ANNOUNCEMENT : NoticesMailTemplateConstants.NOTICE_TYPE_NOTIFICATION;
        return LocaleUtil.getLocalizedMessageFromBundle(bundleName, code, locale);
    }
}
