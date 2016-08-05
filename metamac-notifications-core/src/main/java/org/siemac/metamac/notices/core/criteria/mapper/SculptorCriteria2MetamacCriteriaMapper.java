package org.siemac.metamac.notices.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.domain.Notice;

public interface SculptorCriteria2MetamacCriteriaMapper {

    public MetamacCriteriaResult<NoticeDto> pageResultToMetamacCriteriaResultNoticeDto(PagedResult<Notice> source, Integer pageSize) throws MetamacException;
}
