package org.siemac.metamac.notices.web.server.handlers;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.notices.web.server.rest.AccessControlRestInternalFacadeImpl;
import org.siemac.metamac.notices.web.server.rest.RestMapper;
import org.siemac.metamac.notices.web.shared.GetAccessControlRolesAndAppsAction;
import org.siemac.metamac.notices.web.shared.GetAccessControlRolesAndAppsResult;
import org.siemac.metamac.notices.web.shared.dto.AppDto;
import org.siemac.metamac.notices.web.shared.dto.RoleDto;
import org.siemac.metamac.rest.access_control.v1_0.domain.Apps;
import org.siemac.metamac.rest.access_control.v1_0.domain.Roles;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetAccessControlRolesAndAppsActionHandler extends SecurityActionHandler<GetAccessControlRolesAndAppsAction, GetAccessControlRolesAndAppsResult> {

    @Autowired
    private RestMapper                          restMapper;

    @Autowired
    private AccessControlRestInternalFacadeImpl accessControlRestInternalFacadeImpl;

    public GetAccessControlRolesAndAppsActionHandler() {
        super(GetAccessControlRolesAndAppsAction.class);
    }

    @Override
    public GetAccessControlRolesAndAppsResult executeSecurityAction(GetAccessControlRolesAndAppsAction action) throws ActionException {
        ServiceContext serviceContext = ServiceContextHolder.getCurrentServiceContext();
        Roles roles = accessControlRestInternalFacadeImpl.retrieveRoles(serviceContext);
        Apps apps = accessControlRestInternalFacadeImpl.retrieveApps(serviceContext);
        List<RoleDto> roleDtos = restMapper.buildRoleDtos(roles);
        List<AppDto> appDtos = restMapper.buildAppDtos(apps);
        return new GetAccessControlRolesAndAppsResult(roleDtos, appDtos);
    }
}
