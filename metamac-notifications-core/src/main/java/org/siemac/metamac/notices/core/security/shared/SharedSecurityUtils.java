package org.siemac.metamac.notices.core.security.shared;

import static org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum.ADMINISTRADOR;
import static org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum.ANY_ROLE_ALLOWED;
import static org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum.LECTOR_AVISOS;
import static org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum.REDACTOR_AVISOS;

import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;

public class SharedSecurityUtils {

    /**
     * Checks if logged user has one of the allowed roles
     * 
     * @param roles
     * @return
     */
    protected static boolean isNoticesRoleAllowed(MetamacPrincipal metamacPrincipal, NoticesRoleEnum... roles) {
        // Administration has total control
        if (SharedSecurityUtils.isAdministrador(metamacPrincipal)) {
            return true;
        }
        // Checks user has any role of requested
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                NoticesRoleEnum role = roles[i];
                if (SharedSecurityUtils.isUserInNoticesRol(metamacPrincipal, role)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if logged user has access to a statistical operation with one of the selected roles
     * 
     * @param operationCode
     * @param roles
     * @return
     */
    protected static boolean isOperationAllowed(MetamacPrincipal metamacPrincipal, String operationCode, NoticesRoleEnum... roles) {
        // Administrator has total control in all statistical operations
        if (isAdministrador(metamacPrincipal)) {
            return true;
        }
        // Checks if the statistical operation is in any role
        if (roles != null) {
            for (int i = 0; i < roles.length; i++) {
                NoticesRoleEnum role = roles[i];
                if (haveAccessToOperationInRol(metamacPrincipal, role, operationCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static boolean isOperationAllowedForAnyStatisticalResoueceRole(MetamacPrincipal metamacPrincipal, String operationCode) {
        // Administrator has total control in all statistical operations
        if (isAdministrador(metamacPrincipal)) {
            return true;
        }
        // Checks if the statistical operation is in any role
        for (NoticesRoleEnum role : NoticesRoleEnum.values()) {
            if (haveAccessToOperationInRol(metamacPrincipal, role, operationCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks user has any role
     */
    protected static boolean isUserInNoticesRol(MetamacPrincipal metamacPrincipal, NoticesRoleEnum role) {
        if (ANY_ROLE_ALLOWED.equals(role)) {
            return isAnyNoticesRole(metamacPrincipal);
        } else {
            return isRoleInAccesses(metamacPrincipal, role);
        }
    }

    /**
     * Checks if user has access to an operation. To have access, any access must exists to specified role and operation, or has any access with
     * role and operation with 'null' value
     */
    protected static boolean haveAccessToOperationInRol(MetamacPrincipal metamacPrincipal, NoticesRoleEnum role, String operation) {
        for (MetamacPrincipalAccess metamacPrincipalAccess : metamacPrincipal.getAccesses()) {
            if (NoticesConstants.SECURITY_APPLICATION_ID.equals(metamacPrincipalAccess.getApplication()) && metamacPrincipalAccess.getRole().equals(role.name())) {
                if (metamacPrincipalAccess.getOperation() == null || metamacPrincipalAccess.getOperation().equals(operation)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected static boolean isAdministrador(MetamacPrincipal metamacPrincipal) {
        return isRoleInAccesses(metamacPrincipal, ADMINISTRADOR);
    }

    /**
     * Checks if user has access with role
     */
    protected static boolean isRoleInAccesses(MetamacPrincipal metamacPrincipal, NoticesRoleEnum role) {
        for (MetamacPrincipalAccess metamacPrincipalAccess : metamacPrincipal.getAccesses()) {
            if (NoticesConstants.SECURITY_APPLICATION_ID.equals(metamacPrincipalAccess.getApplication()) && metamacPrincipalAccess.getRole().equals(role.name())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if metamacPrincipal has any of the roles allowed in SRM (except DSD module)
     * 
     * @param metamacPrincipal
     * @return
     */
    protected static boolean isAnyNoticesRole(MetamacPrincipal metamacPrincipal) {
        return isAdministrador(metamacPrincipal) || isLectorAvisos(metamacPrincipal) || isRedactorAvisos(metamacPrincipal);
    }

    protected static boolean isLectorAvisos(MetamacPrincipal metamacPrincipal) {
        return isRoleInAccesses(metamacPrincipal, LECTOR_AVISOS);
    }

    protected static boolean isRedactorAvisos(MetamacPrincipal metamacPrincipal) {
        return isRoleInAccesses(metamacPrincipal, REDACTOR_AVISOS);
    }

    // -----------------------------------------------------------------------
    // NOTICES ACTIONS
    // -----------------------------------------------------------------------

    public static boolean canRetrieveNotice(MetamacPrincipal metamacPrincipal) {
        return isAnyNoticesRole(metamacPrincipal);
    }

    public static boolean canFindNotices(MetamacPrincipal metamacPrincipal) {
        return isAnyNoticesRole(metamacPrincipal);
    }

    public static boolean canMarkNoticeAsRead(MetamacPrincipal metamacPrincipal) {
        return isAnyNoticesRole(metamacPrincipal);
    }

    public static boolean canMarkNoticeAsUnread(MetamacPrincipal metamacPrincipal) {
        return isAnyNoticesRole(metamacPrincipal);
    }

    public static boolean canSendAnnouncement(MetamacPrincipal metamacPrincipal) {
        return isAdministrador(metamacPrincipal) || isRedactorAvisos(metamacPrincipal);
    }
}
