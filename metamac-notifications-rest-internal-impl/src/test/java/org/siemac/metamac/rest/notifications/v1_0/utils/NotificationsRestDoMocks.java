package org.siemac.metamac.rest.notifications.v1_0.utils;

import org.siemac.metamac.notifications.core.notice.domain.Message;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.statistical.resources.core.utils.sql.NotificationsDoMocks;

public class NotificationsRestDoMocks {

    public static Notification mockNotification_TYPE_NOTIFICATION(String urn) {
        Notification notification = buildNotificationWithoutResourcesTypeNotification();
        notification.setUrn(urn);

        return notification;
    }

    private static Notification buildNotificationWithoutResourcesTypeNotification() {
        Notification notification = NotificationsDoMocks.mockNotificationWithoutResources_TYPE_NOTIFICATION();

        // Subject
        notification.setSubject("My subject");

        // Message
        notification.setSendingApplication("TEST-APPLICATION");
        notification.getMessages().clear();
        notification.addMessage(new Message("My message"));
        return notification;
    }

}