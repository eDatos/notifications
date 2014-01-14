package org.siemac.metamac.rest.notifications.v1_0.utils;

import java.math.BigInteger;
import java.util.Date;

import org.siemac.metamac.rest.notifications.v1_0.domain.Message;
import org.siemac.metamac.rest.notifications.v1_0.domain.Messages;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationType;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receiver;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notifications.v1_0.domain.Role;
import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;
import org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperations;

public class NotificationsRestMocks {

    public static Notification mockNotification_TYPE_NOTIFICATION() {
        Notification notification = new Notification();

        notification.setSendingApplication("application");
        notification.setSendingUser("user");
        notification.setExpirationDate(new Date());
        notification.setSubject("My subject");

        {
            // ROLES
            Roles roles = new Roles();
            Role role = new Role();
            role.setName("ADMIN");
            roles.getRoles().add(role);
            roles.setTotal(new BigInteger(String.valueOf(roles.getRoles().size())));
            notification.setRoles(roles);
        }

        {
            // STATISTICAL OPERATIONS
            StatisticalOperations statisticalOperations = new StatisticalOperations();
            org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperation statisticalOperation = new org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperation();
            statisticalOperation.setName("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=operation01");
            statisticalOperations.getStatisticalOperations().add(statisticalOperation);
            statisticalOperations.setTotal(new BigInteger(String.valueOf(statisticalOperations.getStatisticalOperations().size())));
            notification.setStatisticalOperations(statisticalOperations);
        }

        // Messages
        Messages messages = new Messages();
        Message message = new Message();
        message.setText("My message");
        messages.getMessages().add(message);

        notification.setNotificationType(NotificationType.NOTIFICATION);

        // Receivers
        Receivers receivers = new Receivers();
        receivers.setTotal(new BigInteger("5"));
        for (int i = 0; i < 5; i++) {
            Receiver receiver = new Receiver();
            receiver.setUsername("user-" + i);
            receivers.getReceivers().add(receiver);
        }
        notification.setReceivers(receivers);

        return notification;
    }

    public static Notification mockNotification_TYPE_NOTIFICATION(String urn) {
        Notification notification = mockNotification_TYPE_NOTIFICATION();
        notification.setUrn(urn);

        return notification;
    }

}