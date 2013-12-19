package org.siemac.metamac.rest.notifications.v1_0.domain.utils;

import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;
import org.siemac.metamac.rest.notifications.v1_0.domain.enume.MetamacRolesEnum;

public class RolesUtils {

    public static Roles createRolesList(MetamacRolesEnum... rolesCodes) {
        Roles roles = new Roles();
        for (MetamacRolesEnum roleName : rolesCodes) {
            roles.getRoles().add(RoleBuilder.role().withName(roleName.toString()).build());
        }
        return roles;
    }
}
