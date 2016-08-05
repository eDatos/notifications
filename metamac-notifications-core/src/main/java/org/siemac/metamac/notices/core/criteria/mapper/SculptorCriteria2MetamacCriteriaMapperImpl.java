package org.siemac.metamac.notices.core.criteria.mapper;

import java.util.ArrayList;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.mapper.SculptorCriteria2MetamacCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.mapper.NoticeDo2DtoMapper;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SculptorCriteria2MetamacCriteriaMapperImpl implements SculptorCriteria2MetamacCriteriaMapper {

    @Autowired
    private NoticeDo2DtoMapper do2DtoMapper;

    @Override
    public MetamacCriteriaResult<NoticeDto> pageResultToMetamacCriteriaResultNoticeDto(PagedResult<Notice> source, Integer pageSize) throws MetamacException {
        MetamacCriteriaResult<NoticeDto> target = new MetamacCriteriaResult<NoticeDto>();
        target.setPaginatorResult(SculptorCriteria2MetamacCriteria.sculptorResultToMetamacCriteriaResult(source, pageSize));
        if (source.getValues() != null) {
            target.setResults(new ArrayList<NoticeDto>());
            for (Notice item : source.getValues()) {
                target.getResults().add(do2DtoMapper.noticeDoToDto(item));
            }
        }
        return target;
    }
}
