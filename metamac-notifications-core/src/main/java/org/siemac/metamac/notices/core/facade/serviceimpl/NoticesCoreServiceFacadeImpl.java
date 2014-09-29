package org.siemac.metamac.notices.core.facade.serviceimpl;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.criteria.mapper.MetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.notices.core.criteria.mapper.SculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.security.NoticesSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of NoticesCoreServiceFacade.
 */
@Service("noticesCoreServiceFacade")
public class NoticesCoreServiceFacadeImpl extends NoticesCoreServiceFacadeImplBase {

    @Autowired
    private MetamacCriteria2SculptorCriteriaMapper metamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private SculptorCriteria2MetamacCriteriaMapper sculptorCriteria2MetamacCriteriaMapper;

    public NoticesCoreServiceFacadeImpl() {
    }

    @Override
    public MetamacCriteriaResult<NoticeDto> findNotices(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {

        // Security
        NoticesSecurityUtils.canFindNotices(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getNoticeCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<Notice> result = getNoticesService().findNoticeByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        return sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultNoticeDto(result, sculptorCriteria.getPageSize());
    }
}
