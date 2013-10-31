package org.siemac.metamac.notifications.rest.exception;

import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;

public class RestServiceExceptionType extends RestCommonServiceExceptionType {

    public static final RestCommonServiceExceptionType NOTIFICATION_NOT_FOUND = create("exception.notifications.notification.not_found");
}
