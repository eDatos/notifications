package org.siemac.metamac.notices.web.server.handlers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.notices.web.server.rest.RestMapper;
import org.siemac.metamac.notices.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsAction;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsResult;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetStatisticalOperationsActionHandler extends SecurityActionHandler<GetStatisticalOperationsAction, GetStatisticalOperationsResult> {

    @Autowired
    private RestMapper                              restMapper;

    @Autowired
    private StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    public GetStatisticalOperationsActionHandler() {
        super(GetStatisticalOperationsAction.class);
    }

    @Override
    public GetStatisticalOperationsResult executeSecurityAction(GetStatisticalOperationsAction action) throws ActionException {

        int firstResult = 0;
        int totalResults = 0;
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>();
        Operations result = statisticalOperationsRestInternalFacade.findOperations(ServiceContextHolder.getCurrentServiceContext(), action.getCriteria().getFirstResult(), action.getCriteria()
                .getMaxResults(), null, action.getCriteria().getCriteria());
        if (result != null && result.getOperations() != null) {
            firstResult = result.getOffset().intValue();
            totalResults = result.getTotal().intValue();
            externalItemDtos = restMapper.buildExternalItemDtosFromOperations(result.getOperations());
        }
        return new GetStatisticalOperationsResult(externalItemDtos, firstResult, totalResults);
    }
}
