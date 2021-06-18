package org.siemac.metamac.notices.core.error;

import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;

public class ServiceExceptionType extends CommonServiceExceptionType {

    // @formatter:off
    public static final CommonServiceExceptionType NOTICE_NOT_FOUND            = create("exception.notices.notice.not_found");
    public static final CommonServiceExceptionType NOTICE_RECEIVERS_NOT_FOUND  = create("exception.notices.notice.receivers_not_found");
    public static final CommonServiceExceptionType NOTICE_REPLYTO_NOT_FOUND    = create("exception.notices.notice.replyTo_not_found");
    public static final CommonServiceExceptionType RECEIVER_NOT_FOUND          = create("exception.notices.receiver.not_found");
    public static final CommonServiceExceptionType RECEIVER_USERNAME_NOT_FOUND = create("exception.notices.receiver.username_not_found");

    public static final CommonServiceExceptionType RECEIVER_NULL          = create("exception.notices.receiver.null");
    // @formatter:on
}
