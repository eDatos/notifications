package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import java.math.BigInteger;

import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;

public class RolesUtils {

    public static Roles createRolesList(MetamacRolesEnum... rolesCodes) {
        Roles roles = new Roles();
        for (MetamacRolesEnum roleName : rolesCodes) {
            roles.getRoles().add(RoleBuilder.role().withName(roleName.toString()).build());
        }

        roles.setTotal(new BigInteger(String.valueOf(roles.getRoles().size())));
        return roles;
    }
}
