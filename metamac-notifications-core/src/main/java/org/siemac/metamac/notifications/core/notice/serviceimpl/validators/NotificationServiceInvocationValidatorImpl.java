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
        checkExistingNotification(notification, exceptions);
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

    public static void checkRetrieveNotificationByUrn(String urn, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
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

        checkNotificationMetadata(notification, exceptions);

        // Metadata that must be empty for new entities
        ValidationUtils.checkMetadataEmpty(notification.getId(), ServiceExceptionParameters.NOTIFICATION__ID, exceptions);
        ValidationUtils.checkMetadataEmpty(notification.getVersion(), ServiceExceptionParameters.NOTIFICATION__VERSION, exceptions);
        ValidationUtils.checkMetadataEmpty(notification.getUrn(), ServiceExceptionParameters.NOTIFICATION__URN, exceptions);
    }

    private static void checkExistingNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkParameterRequired(notification, ServiceExceptionParameters.NOTIFICATION, exceptions);

        if (notification == null) {
            return;
        }

        if (notification.isMark()) {
            // TODO lanzar exepcion de modificar un aviso que ya está marcado
            throw new RuntimeException("The notification is unmodifiable, is already marked.");
        }

        checkNotificationMetadata(notification, exceptions);
    }

    private static void checkNotificationMetadata(Notification notification, List<MetamacExceptionItem> exceptions) {
        ValidationUtils.checkMetadataRequired(notification.getSendingUser(), ServiceExceptionParameters.NOTIFICATION__SENDING_USER, exceptions);
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
    }

}
