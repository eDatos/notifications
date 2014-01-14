package org.siemac.metamac.rest.notifications.v1_0.utils;

import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.statistical.resources.core.utils.sql.NotificationsDoMocks;

public class NotificationsRestDoMocks {

    public static Notification mockNotification_TYPE_NOTIFICATION(String urn) {
        Notification notification = buildNotificationWithResourcesTypeNotification();
        notification.setUrn(urn);

        return notification;
    }

    private static Notification buildNotificationWithResourcesTypeNotification() {
        Notification notification = NotificationsDoMocks.mockNotificationWithResources_TYPE_NOTIFICATION();
        return notification;
    }

}