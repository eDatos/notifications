package org.siemac.metamac.notices.core.security;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.security.shared.SharedSecurityUtils;
import org.siemac.metamac.sso.utils.SecurityUtils;

public class NoticesSecurityUtils extends SecurityUtils {

    public static void canRetrieveNotice(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canRetrieveNotice(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canFindNotices(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canFindNotices(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canMarkNoticeAsRead(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canMarkNoticeAsRead(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canMarkNoticeAsUnread(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canMarkNoticeAsUnread(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }

    public static void canSendAnnouncement(ServiceContext ctx) throws MetamacException {
        if (!SharedSecurityUtils.canSendAnnouncement(getMetamacPrincipal(ctx))) {
            throwExceptionIfOperationNotAllowed(ctx);
        }
    }
}
