package org.siemac.metamac.notices.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.access_control.v1_0.domain.Apps;
import org.siemac.metamac.rest.access_control.v1_0.domain.Roles;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface AccessControlRestInternalFacade {

    public Roles retrieveRoles(ServiceContext serviceContext) throws MetamacWebException;
    public Apps retrieveApps(ServiceContext serviceContext) throws MetamacWebException;
}
