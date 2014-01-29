package org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;

public interface NoticesRest2DoMapperV10 {

    public org.siemac.metamac.notices.core.notice.domain.Notice noticeRestToEntity(ServiceContext ctx, Notice notice) throws MetamacException;

}
