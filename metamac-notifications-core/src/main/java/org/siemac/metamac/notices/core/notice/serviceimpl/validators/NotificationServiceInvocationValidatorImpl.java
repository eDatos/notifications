package org.siemac.metamac.notices.core.notice.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.notices.core.error.ServiceExceptionParameters;
import org.siemac.metamac.notices.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.notices.core.notice.domain.Notification;
import org.siemac.metamac.notices.core.notice.enume.domain.NotificationType;
import org.siemac.metamac.notices.core.utils.NotificationsValidationUtils;

public class NotificationServiceInvocationValidatorImpl {

    // ------------------------------------------------------------------------------------
    // NOTIFICATIONS
    // ------------------------------------------------------------------------------------

    public static void checkFindNotificationById(Long id, List<MetamacExceptionItem> exceptions) {
        NotificationsValidationUtils.checkParameterRequired(id, ServiceExceptionSingleParameters.ID, exceptions);
    }

    public static void checkCreateNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        checkNewNotification(notification, exceptions);
    }

    public static void checkUpdateNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        checkExistingNotification(notification, exceptions);
    }

    public static void checkDeleteNotification(Long id, List<MetamacExceptionItem> exceptions) {
        NotificationsValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);
    }

    public static void checkFindAllNotification(List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkFindNotificationByCondition(List<ConditionalCriteria> condition, List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkRetrieveNotificationByUrn(String urn, List<MetamacExceptionItem> exceptions) {
        NotificationsValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    // NOTIFICATIONS
    // --------------

    private static void checkNewNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        NotificationsValidationUtils.checkParameterRequired(notification, ServiceExceptionParameters.NOTIFICATION, exceptions);

        if (notification == null) {
            return;
        }

        checkNotificationMetadata(notification, exceptions);

        // Metadata that must be empty for new entities
        NotificationsValidationUtils.checkMetadataEmpty(notification.getId(), ServiceExceptionParameters.NOTIFICATION__ID, exceptions);
        NotificationsValidationUtils.checkMetadataEmpty(notification.getVersion(), ServiceExceptionParameters.NOTIFICATION__VERSION, exceptions);
        NotificationsValidationUtils.checkMetadataEmpty(notification.getUrn(), ServiceExceptionParameters.NOTIFICATION__URN, exceptions);
    }

    private static void checkExistingNotification(Notification notification, List<MetamacExceptionItem> exceptions) {
        NotificationsValidationUtils.checkParameterRequired(notification, ServiceExceptionParameters.NOTIFICATION, exceptions);

        if (notification == null) {
            return;
        }

        if (notification.isMark()) {
            // TODO lanzar exepcion de modificar un aviso que ya está marcado
            // TODO EN realidad este campo no se puede mapear en este servicio. Se modifica en un servicio diferente
            throw new RuntimeException("The notification is unmodifiable, is already marked.");
        }

        checkNotificationMetadata(notification, exceptions);
    }

    private static void checkNotificationMetadata(Notification notification, List<MetamacExceptionItem> exceptions) {
        NotificationsValidationUtils.checkMetadataRequired(notification.getSendingUser(), ServiceExceptionParameters.NOTIFICATION__SENDING_USER, exceptions);
        NotificationsValidationUtils.checkMetadataRequired(notification.getNotificationType(), ServiceExceptionParameters.NOTIFICATION__NOTIFICATION_TYPE, exceptions);
        NotificationsValidationUtils.checkMetadataRequired(notification.getSubject(), ServiceExceptionParameters.NOTIFICATION__SUBJECT, exceptions);
        NotificationsValidationUtils.checkMetadataRequired(notification.getMessages(), ServiceExceptionParameters.NOTIFICATION__MESSAGES, exceptions);

        // when notificationType is "ADVERTISEMENT"
        if (NotificationType.ADVERTISEMENT.equals(notification.getNotificationType())) {
            NotificationsValidationUtils.checkMetadataRequired(notification.getExpirationDate(), ServiceExceptionParameters.NOTIFICATION__EXPIRATION_DATE, exceptions);
        }

    }

}
