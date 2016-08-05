package org.siemac.metamac.notices.core.criteria.mapper;

import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.notices.core.notice.domain.Notice;

public interface MetamacCriteria2SculptorCriteriaMapper {

    public MetamacCriteria2SculptorCriteria<Notice> getNoticeCriteriaMapper();
}
