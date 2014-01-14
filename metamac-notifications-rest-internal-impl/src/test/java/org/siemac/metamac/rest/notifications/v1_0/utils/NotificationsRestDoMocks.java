package org.siemac.metamac.rest.notifications.v1_0.utils;

import org.joda.time.DateTime;
import org.siemac.metamac.notifications.core.notice.domain.Message;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.domain.Receiver;
import org.siemac.metamac.notifications.core.notice.domain.Role;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationType;
import org.siemac.metamac.statistical.resources.core.utils.sql.NotificationsDoMocks;

public class NotificationsRestDoMocks {

    public static Notification mockNotification_TYPE_NOTIFICATION() {
        Notification notification = new Notification("application", "My subject", NotificationType.NOTIFICATION);
        notification.setExpirationDate(new DateTime(2013, 1, 1, 1, 1, 1, 1));
        notification.getMessages().add(new Message("My message"));

        {
            // role
            Role role = new Role();
            role.setName("ADMIN");
            notification.addRole(role);
        }

        {
            // statistical operation
            notification.addStatisticalOperation(NotificationsDoMocks.mockStatisticalOperationExternalItem("operation01"));
        }

        // Receivers
        for (int i = 0; i < 5; i++) {
            Receiver receiverElement = new Receiver();
            receiverElement.setUsername("user-" + i);
            notification.addReceiver(receiverElement);
        }

        return notification;
    }

    public static Notification mockNotification_TYPE_NOTIFICATION(String urn) {
        Notification notification = mockNotification_TYPE_NOTIFICATION();
        notification.setUrn(urn);

        return notification;
    }

}