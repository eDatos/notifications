package org.siemac.metamac.rest.notifications.v1_0.utils;

import java.util.Date;

import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessagesUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.ReceiversUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.RolesUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.StatisticalOperationsUtils;
import org.siemac.metamac.rest.notifications.v1_0.domain.Messages;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationType;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;
import org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperations;

public class NotificationsRestMocks {

    public static Notification mockNotification_TYPE_NOTIFICATION() {
        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.NOTIFICATION);

        notification.setSendingApplication("application");
        notification.setSendingUser("user");
        notification.setExpirationDate(new Date());
        notification.setSubject("My subject");

        // Roles
        Roles roles = RolesUtils.createRolesList(MetamacRolesEnum.ADMINISTRADOR);
        notification.setRoles(roles);

        // Statistical operations
        StatisticalOperations statisticalOperations = StatisticalOperationsUtils.createStatisticalOperationsList("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=operation01");
        notification.setStatisticalOperations(statisticalOperations);

        // Receivers
        Receivers receivers = ReceiversUtils.createReceiversList("user-1", "user-2", "user-3", "user-4", "user-5");
        notification.setReceivers(receivers);

        // Messages
        Messages messages = MessagesUtils.createMessagesList("My message");
        notification.setMessages(messages);

        return notification;
    }

    public static Notification mockNotification_TYPE_NOTIFICATION(String urn) {
        Notification notification = mockNotification_TYPE_NOTIFICATION();
        notification.setUrn(urn);

        return notification;
    }

}