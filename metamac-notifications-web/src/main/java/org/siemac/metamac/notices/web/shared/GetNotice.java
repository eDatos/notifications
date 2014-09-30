package org.siemac.metamac.notices.web.shared;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetNotice {

    @In(1)
    NoticeDto         notice;

    @In(2)
    NoticeWebCriteria criteria;

    @Out(1)
    NoticeDto         updatedNotice;

    @Out(2)
    List<NoticeDto>   notices;

    @Out(3)
    Integer           firstResult;

    @Out(4)
    Integer           totalResults;
}
