package org.siemac.metamac.notifications.rest.v1_0.mapper.notification;

import java.util.HashSet;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notifications.core.error.ServiceExceptionType;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationConditionType;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationType;
import org.siemac.metamac.notifications.core.notice.serviceapi.NotificationService;
import org.siemac.metamac.notifications.rest.v1_0.mapper.base.BaseRest2DoMapperV10Impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsRest2DoMapperV10Impl extends BaseRest2DoMapperV10Impl implements NotificationsRest2DoMapperV10 {

    @Autowired
    private NotificationService notificationService;

    @Override
    public Notification notificationRest2Entity(ServiceContext ctx, org.siemac.metamac.rest.notifications.v1_0.domain.Notification source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Find
        Notification notification = notificationService.retrieveNotificationByUrn(ctx, source.getUrn());

        Notification target = null;

        // Update
        if (notification != null) {
            target = notification;
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.NOTIFICATION_ALREADY_EXISTS).withMessageParameters(source.getUrn()).build();
        } else {
            // New Object
            target = new Notification(source.getSendingApplication(), source.getMessage(), NotificationType.valueOf(source.getNotificationType().name()));
        }

        target.setSendingUser(source.getSendingUser());
        target.setMail(source.getEmail());
        target.setExpirationDate(CoreCommonUtil.transformDateToDateTime(source.getExpirationDate()));
        target.setNotificationConditionType((source.getNotificationConditionType() != null) ? NotificationConditionType.valueOf(source.getNotificationConditionType().name()) : null);
        target.setRequiredRole(source.getRequiredRole());
        target.setMessage(source.getMessage());

        // Receivers
        if (source.getReceivers() != null) {
            Set<String> usernamesInNotifications = new HashSet<String>();
            for (org.siemac.metamac.rest.notifications.v1_0.domain.Receiver sourceReceiver : source.getReceivers().getReceivers()) {
                // TODO chequear con el api del access control, los usuarios que son válidos
                if (!usernamesInNotifications.contains(sourceReceiver.getUsername())) {
                    usernamesInNotifications.add(sourceReceiver.getUsername());
                    org.siemac.metamac.notifications.core.notice.domain.Receiver receiverElement = new org.siemac.metamac.notifications.core.notice.domain.Receiver();
                    receiverElement.setUsername(sourceReceiver.getUsername());
                    target.addReceiver(receiverElement);
                }
            }
        }

        return target;
    }
}
