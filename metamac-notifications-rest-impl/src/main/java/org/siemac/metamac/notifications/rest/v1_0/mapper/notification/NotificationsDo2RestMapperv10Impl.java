package org.siemac.metamac.notifications.rest.v1_0.mapper.notification;

import java.math.BigInteger;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notifications.core.notice.domain.Receiver;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationConditionType;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationType;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;
import org.springframework.stereotype.Component;

@Component
public class NotificationsDo2RestMapperv10Impl implements NotificationsDo2RestMapperV10 {

    @Override
    public Notification notificationEntity2Rest(ServiceContext ctx, org.siemac.metamac.notifications.core.notice.domain.Notification source) throws MetamacException {
        if (source == null) {
            return null;
        }

        Notification target = new Notification();

        target.setUrn(source.getUrn());
        target.setSendingApplication(source.getSendingApplication());
        target.setSendingUser(source.getSendingUser());
        target.setSendingDate(CoreCommonUtil.transformDateTimeToDate(source.getCreatedDate()));
        target.setEmail(source.getMail());
        target.setExpirationDate(CoreCommonUtil.transformDateTimeToDate(source.getExpirationDate()));
        target.setRequiredRole(source.getRequiredRole());
        target.setMessage(source.getMessage());
        target.setNotificationType(NotificationType.fromValue(source.getNotificationType().getName()));
        target.setNotificationConditionType((source.getNotificationConditionType() != null) ? NotificationConditionType.fromValue(source.getNotificationConditionType().getName()) : null);

        // Receivers
        Receivers receivers = new Receivers();
        for (Receiver sourceReceiver : source.getReceivers()) {
            org.siemac.metamac.rest.notifications.v1_0.domain.Receiver targetReceiver = new org.siemac.metamac.rest.notifications.v1_0.domain.Receiver();
            targetReceiver.setUsername(sourceReceiver.getUsername());
            receivers.getReceivers().add(targetReceiver);
        }
        if (receivers.getReceivers().size() != 0) {
            receivers.setTotal(new BigInteger(String.valueOf(receivers.getReceivers().size())));
            target.setReceivers(receivers);
        }

        return target;
    }
}
