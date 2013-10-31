package org.siemac.metamac.notifications.core.notice.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.notifications.core.error.ServiceExceptionParameters;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationConditionType;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationType;

public class NotificationServiceInvocationValidatorImpl {

    public static void checkFindNotificationById(Long id, List<MetamacExceptionItem> exceptions) {
        // TODO checkFindNotificationById
    }

    public static void checkCreateNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        checkNewNotification(notification, exceptions);
    }

    public static void checkUpdateNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        checkNewNotification(notification, exceptions);
    }

    public static void checkDeleteNotification(Long id, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);
    }

    public static void checkFindAllNotification(List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkFindNotificationByCondition(List<ConditionalCriteria> condition, List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    // NOTIFICATIONS
    // --------------

    private static void checkNewNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(notification, ServiceExceptionParameters.NOTIFICATION, exceptions);

        if (notification == null) {
            return;
        }

        ValidationUtils.checkMetadataRequired(notification.getAppIdIssuer(), ServiceExceptionParameters.NOTIFICATION__APP_ID_ISSUER, exceptions);
        ValidationUtils.checkMetadataRequired(notification.getNotificationType(), ServiceExceptionParameters.NOTIFICATION__NOTIFICATION_TYPE, exceptions);
        ValidationUtils.checkMetadataRequired(notification.getMessage(), ServiceExceptionParameters.NOTIFICATION__MESSAGE, exceptions);

        // when notificationType is "ADVERTISEMENT"
        if (NotificationType.ADVERTISEMENT.equals(notification.getNotificationType())) {
            ValidationUtils.checkMetadataRequired(notification.getExpirationDate(), ServiceExceptionParameters.NOTIFICATION__EXPIRATION_DATE, exceptions);
        }

        // when notificationConditionType is "ROLE_ACCESS"
        if (NotificationConditionType.ROLE_ACCESS.equals(notification.getNotificationConditionType())) {
            ValidationUtils.checkMetadataRequired(notification.getRequiredRole(), ServiceExceptionParameters.NOTIFICATION__REQUIRED_ROLE, exceptions);
        }

        // Metadata that must be empty for new entities
    }

    private static void checkExistingNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(notification, ServiceExceptionParameters.NOTIFICATION, exceptions);

        if (notification == null) {
            return;
        }

        if (notification.isMark()) {
            // TODO lanzar exepcion de modificar un aviso que ya está marcado
        }

        checkNewNotification(notification, exceptions);
    }
}
