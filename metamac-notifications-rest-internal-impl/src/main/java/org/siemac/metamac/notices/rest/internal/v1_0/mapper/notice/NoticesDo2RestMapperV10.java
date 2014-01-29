package org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.notice.domain.Notice;

public interface NoticesDo2RestMapperV10 {

    public org.siemac.metamac.rest.notices.v1_0.domain.Notice noticeEntity2Rest(ServiceContext ctx, Notice source) throws MetamacException;

}
