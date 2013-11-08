package org.siemac.metamac.notifications.core.channel.mail.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notifications.core.conf.NotificationsConfiguration;
import org.siemac.metamac.notifications.core.constants.NotificationsConstants;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MailChannelServiceImpl implements MailChannelService {

    private static final String        SUBJECT_NOTIFICATION_KEY   = "info.notifications.channel.mail.subject.notification";
    private String                     subjectNotificationMessage = null;

    @Autowired
    private JavaMailSender             mailSender;

    @Autowired
    private VelocityEngine             velocityEngine;

    @Autowired
    private NotificationsConfiguration notificationsConfiguration;

    @Override
    public void sendMail(ServiceContext serviceContext, final Notification notification, final String[] mailsTo) throws MetamacException {
        // Prepare email
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // Mail configuration
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setSubject(getNotificationMailSubject());
                message.setTo(mailsTo);

                if (StringUtils.isEmpty(notification.getMail())) {
                    message.setFrom(notificationsConfiguration.retrieveChannelMailUsername());
                } else {
                    message.setFrom(notification.getMail());
                }

                // Mail data
                Map model = new HashMap();
                model.put("notification", notification);
                model.put("createdDate", CoreCommonUtil.transformDateTimeToISODateTimeLexicalRepresentation(notification.getCreatedDate()));

                if (NotificationType.ADVERTISEMENT.equals(notification.getNotificationType())) {
                    model.put("expirationDate", CoreCommonUtil.transformDateTimeToISODateTimeLexicalRepresentation(notification.getExpirationDate()));
                }

                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, NotificationsConstants.CHANNEL_MAIL_TEMPLATE_NOTIFICATION, "UTF-8", model);
                message.setText(text, false); // Text Plain
            }

        };

        this.mailSender.send(preparator);
    }

    private String getNotificationMailSubject() throws MetamacException {
        if (subjectNotificationMessage == null) {
            subjectNotificationMessage = LocaleUtil.getMessageForCode(SUBJECT_NOTIFICATION_KEY, LocaleUtil.getLocaleFromLocaleString(notificationsConfiguration.retrieveLanguageDefault()));
        }
        return subjectNotificationMessage;
    }
}
