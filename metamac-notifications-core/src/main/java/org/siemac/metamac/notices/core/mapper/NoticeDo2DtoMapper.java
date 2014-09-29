package org.siemac.metamac.notices.core.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.domain.Notice;

public interface NoticeDo2DtoMapper {

    public NoticeDto noticeDoToDto(Notice source) throws MetamacException;
}
