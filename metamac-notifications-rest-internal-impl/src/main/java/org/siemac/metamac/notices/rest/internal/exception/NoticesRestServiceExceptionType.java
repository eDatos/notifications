package org.siemac.metamac.notices.rest.internal.exception;

import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;

public class NoticesRestServiceExceptionType extends RestCommonServiceExceptionType {

    public static final RestCommonServiceExceptionType NOTICE_NOT_FOUND = create("exception.notices.notice.not_found");

}
