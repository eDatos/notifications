package org.siemac.metamac.notices.web.client.utils;

import org.siemac.metamac.notices.core.security.shared.SharedSecurityUtils;
import org.siemac.metamac.notices.web.client.NoticesWeb;
import org.siemac.metamac.sso.client.MetamacPrincipal;

public class ClientSecurityUtils {

    public static boolean canRetrieveNotice() {
        return SharedSecurityUtils.canRetrieveNotice(getMetamacPrincipal());
    }

    public static boolean canFindNotices() {
        return SharedSecurityUtils.canFindNotices(getMetamacPrincipal());
    }

    public static boolean canMarkNoticeAsRead() {
        return SharedSecurityUtils.canMarkNoticeAsRead(getMetamacPrincipal());
    }

    public static boolean canMarkNoticeAsUnread() {
        return SharedSecurityUtils.canMarkNoticeAsUnread(getMetamacPrincipal());
    }

    public static boolean canSendAnnouncement() {
        return SharedSecurityUtils.canSendAnnouncement(getMetamacPrincipal());
    }

    private static MetamacPrincipal getMetamacPrincipal() {
        return NoticesWeb.getCurrentUser();
    }
}
