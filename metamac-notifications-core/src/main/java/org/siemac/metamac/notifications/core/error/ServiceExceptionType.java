package org.siemac.metamac.notifications.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType NOTIFICATION_NOT_FOUND           = create("exception.notifications.notification.not_found");
    public static final CommonServiceExceptionType NOTIFICATION_ALREADY_EXISTS      = create("exception.notifications.notification.already_exists");
    public static final CommonServiceExceptionType NOTIFICATION_RECEIVERS_NOT_FOUND = create("exception.notifications.notification.receivers_not_found");
}
