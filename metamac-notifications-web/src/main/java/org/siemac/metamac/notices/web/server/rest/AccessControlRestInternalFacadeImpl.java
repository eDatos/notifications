package org.siemac.metamac.notices.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.notices.core.error.ServiceExceptionParameters;
import org.siemac.metamac.notices.core.invocation.service.MetamacApisLocator;
import org.siemac.metamac.rest.access_control.v1_0.domain.Apps;
import org.siemac.metamac.rest.access_control.v1_0.domain.Roles;
import org.siemac.metamac.web.common.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccessControlRestInternalFacadeImpl implements AccessControlRestInternalFacade {

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Autowired
    private RestExceptionUtils restExceptionUtils;

    @Override
    public Roles retrieveRoles(ServiceContext serviceContext) throws MetamacWebException {
        try {
            return restApiLocator.getAccessControlRestInternalFacadeV1_0().findRoles();
        } catch (Exception e) {
            throw manageRestException(serviceContext, e);
        }
    }

    @Override
    public Apps retrieveApps(ServiceContext serviceContext) throws MetamacWebException {
        try {
            return restApiLocator.getAccessControlRestInternalFacadeV1_0().findApps();
        } catch (Exception e) {
            throw manageRestException(serviceContext, e);
        }
    }

    //
    // EXCEPTION HANDLERS
    //

    private MetamacWebException manageRestException(ServiceContext ctx, Exception e) throws MetamacWebException {
        return restExceptionUtils.manageMetamacRestException(ctx, e, ServiceExceptionParameters.API_ACCESS_CONTROL_INTERNAL, restApiLocator.getAccessControlRestInternalFacadeV1_0());
    }
}
