package org.siemac.metamac.notices.core.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeCreationResultDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeCreationResult;

public interface NoticeDo2DtoMapper {

    public NoticeDto noticeDoToDto(Notice source) throws MetamacException;
    public NoticeCreationResultDto noticeCreationResultDo2Dto(NoticeCreationResult source) throws MetamacException;
}
