package org.siemac.metamac.notices.core.invocation.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.error.ServiceExceptionParameters;
import org.siemac.metamac.notices.core.error.ServiceExceptionUtils;
import org.siemac.metamac.rest.access_control.v1_0.domain.Apps;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.siemac.metamac.rest.access_control.v1_0.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("accessControlRestInternalFacade")
public class AccessControlRestInternalFacadeImpl implements AccessControlRestInternalFacade {

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public List<User> findUsers(String query) throws MetamacException {
        try {
            String limit = "1000";
            int offset = 0;
            List<User> results = new ArrayList<User>();
            Users users = null;
            do {
                users = restApiLocator.getAccessControlRestInternalFacadeV1_0().findUsers(query, limit, String.valueOf(offset));
                results.addAll(users.getUsers());
                offset += users.getUsers().size(); // next page
            } while (users.getTotal().intValue() != results.size());
            return results;
        } catch (ServerWebApplicationException e) {
            throw manageAccessControlInternalRestException(e);
        }
    }

    @Override
    public Apps retrieveApps(ServiceContext serviceContext) throws MetamacException {
        try {
            return restApiLocator.getAccessControlRestInternalFacadeV1_0().findApps();
        } catch (Exception e) {
            throw manageAccessControlInternalRestException(e);
        }
    }

    // -------------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------------

    private MetamacException manageAccessControlInternalRestException(Exception e) throws MetamacException {
        return ServiceExceptionUtils.manageMetamacRestException(e, ServiceExceptionParameters.API_ACCESS_CONTROL_INTERNAL, restApiLocator.getAccessControlRestInternalFacadeV1_0());
    }
}
