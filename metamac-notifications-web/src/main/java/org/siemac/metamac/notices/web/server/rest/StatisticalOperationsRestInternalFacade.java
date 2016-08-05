package org.siemac.metamac.notices.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface StatisticalOperationsRestInternalFacade {

    public Operations findOperations(ServiceContext serviceContext, int firstResult, int maxResult, String[] operationCodes, String criteria) throws MetamacWebException;
}
