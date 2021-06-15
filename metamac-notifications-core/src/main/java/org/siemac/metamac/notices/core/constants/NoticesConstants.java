package org.siemac.metamac.notices.core.constants;

public final class NoticesConstants {

    private NoticesConstants() {
    }

    public static final String SECURITY_APPLICATION_ID                  = "GESTOR_AVISOS";

    public static final String GESTOR_ACCESOS_APPLICATION_ID            = "GESTOR_ACCESOS";

    // Mail channel
    public static final String CHANNEL_MAIL_TEMPLATE_NOTIFICATION_PLAIN = "mail_templates/mail_notification_plain.vm";
    public static final String CHANNEL_MAIL_TEMPLATE_NOTIFICATION_HTML  = "mail_templates/mail_notification_html.vm";

    public static final String CHANNEL_MAIL_TEMPLATE_EXTERNAL_USER_NOTIFICATION_PLAIN = "mail_templates/mail_external_user_notification_plain.vm";
    public static final String CHANNEL_MAIL_TEMPLATE_EXTERNAL_USER_NOTIFICATION_HTML  = "mail_templates/mail_external_user_notification_html.vm";
}
