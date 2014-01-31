package org.siemac.metamac.notices.core.channel.mail.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notices.core.conf.NoticesConfiguration;
import org.siemac.metamac.notices.core.constants.NoticesConstants;
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

    private static final String  SUBJECT_NOTIFICATION_KEY = "info.notices.channel.mail.subject.notice";
    private String               subjectNoticeMessage     = null;

    @Autowired
    private JavaMailSender       mailSender;

    @Autowired
    private VelocityEngine       velocityEngine;

    @Autowired
    private NoticesConfiguration noticesConfiguration;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void sendMail(ServiceContext serviceContext, final Notice notice, final String[] mailsTo, final String replyTo) throws MetamacException {
        // Prepare email
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // Mail configuration
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setSubject(getNoticeMailSubject());
                message.setTo(mailsTo);
                message.setReplyTo(replyTo);
                message.setFrom(noticesConfiguration.retrieveChannelMailUsername());

                // Mail data
                Map model = new HashMap();
                model.put("notice", notice);
                model.put("createdDate", CoreCommonUtil.transformDateTimeToISODateTimeLexicalRepresentation(notice.getCreatedDate()));

                if (NoticeType.ANNOUNCEMENT.equals(notice.getNoticeType())) {
                    model.put("expirationDate", CoreCommonUtil.transformDateTimeToISODateTimeLexicalRepresentation(notice.getExpirationDate()));
                }

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, NoticesConstants.CHANNEL_MAIL_TEMPLATE_NOTIFICATION, "UTF-8", model);
                message.setText(text, false); // Text Plain
            }

        };

        this.mailSender.send(preparator);
    }

    private String getNoticeMailSubject() throws MetamacException {
        if (subjectNoticeMessage == null) {
            subjectNoticeMessage = LocaleUtil.getMessageForCode(SUBJECT_NOTIFICATION_KEY, LocaleUtil.getLocaleFromLocaleString(noticesConfiguration.retrieveLanguageDefault()));
        }
        return subjectNoticeMessage;
    }
}
