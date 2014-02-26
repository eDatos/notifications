package org.siemac.metamac.notices.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    public static final CommonServiceExceptionType NOTICE_NOT_FOUND           = create("exception.notices.notice.not_found");
    public static final CommonServiceExceptionType NOTICE_RECEIVERS_NOT_FOUND = create("exception.notices.notice.receivers_not_found");
}
