package org.siemac.metamac.notices.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.web.shared.dto.AppDto;
import org.siemac.metamac.notices.web.shared.dto.RoleDto;

public class AccessControlValues {

    private static List<RoleDto> roles = new ArrayList<RoleDto>();
    private static List<AppDto>  apps  = new ArrayList<AppDto>();

    public static String getAppTitle(String appCode) {
        for (AppDto app : apps) {
            if (StringUtils.equals(app.getCode(), appCode)) {
                return app.getTitle();
            }
        }
        return appCode;
    }

    public static String getRoleTitle(String roleCode) {
        for (RoleDto app : roles) {
            if (StringUtils.equals(app.getCode(), roleCode)) {
                return app.getTitle();
            }
        }
        return roleCode;
    }

    public static List<RoleDto> getRoles() {
        return roles;
    }

    public static void setRoles(List<RoleDto> roles) {
        AccessControlValues.roles = roles;
    }

    public static List<AppDto> getApps() {
        return apps;
    }

    public static void setApps(List<AppDto> apps) {
        AccessControlValues.apps = apps;
    }
}
