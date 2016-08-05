package org.siemac.metamac.notices.web.shared;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetStatisticalOperations {

    @In(1)
    PaginationWebCriteria criteria;

    @Out(1)
    List<ExternalItemDto> operations;

    @Out(2)
    int                   firstResult;

    @Out(3)
    int                   totalResults;
}
