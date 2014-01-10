package org.siemac.metamac.notifications.rest.internal.exception;

import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;

public class NotificationsRestServiceExceptionType extends RestCommonServiceExceptionType {

    public static final RestCommonServiceExceptionType NOTIFICATION_NOT_FOUND = create("exception.notifications.notification.not_found");

}
