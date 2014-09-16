package org.siemac.metamac.notices.web.shared;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetNotices {

    @In(1)
    NoticeWebCriteria criteria;

    @Out(1)
    List<NoticeDto>   notices;

    @Out(2)
    Integer           firstResult;

    @Out(3)
    Integer           totalResults;
}
