package org.siemac.metamac.notices.web.server.handlers;

import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.facade.serviceapi.NoticesServiceFacade;
import org.siemac.metamac.notices.web.server.utils.MetamacWebCriteriaUtils;
import org.siemac.metamac.notices.web.shared.GetNoticesAction;
import org.siemac.metamac.notices.web.shared.GetNoticesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetNoticesActionHandler extends SecurityActionHandler<GetNoticesAction, GetNoticesResult> {

    @Autowired
    private NoticesServiceFacade noticesServiceFacade;

    public GetNoticesActionHandler() {
        super(GetNoticesAction.class);
    }

    @Override
    public GetNoticesResult executeSecurityAction(GetNoticesAction action) throws ActionException {
        try {
            MetamacCriteria criteria = MetamacWebCriteriaUtils.build(action.getCriteria());
            MetamacCriteriaResult<NoticeDto> result = noticesServiceFacade.findNotices(ServiceContextHolder.getCurrentServiceContext(), criteria);
            return new GetNoticesResult(result.getResults(), result.getPaginatorResult().getFirstResult(), result.getPaginatorResult().getTotalResults());
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
