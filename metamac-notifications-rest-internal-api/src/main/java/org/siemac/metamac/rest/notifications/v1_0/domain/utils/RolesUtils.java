package org.siemac.metamac.rest.notifications.v1_0.domain.utils;

import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;

public class RolesUtils {

    public static Roles createRolesList(String... rolesCodes) {
        Roles roles = new Roles();
        for (String roleName : rolesCodes) {
            roles.getRoles().add(RoleBuilder.role().withName(roleName).build());
        }
        return roles;
    }
}
