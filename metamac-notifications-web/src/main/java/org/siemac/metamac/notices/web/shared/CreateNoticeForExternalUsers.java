package org.siemac.metamac.notices.web.shared;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;
import org.siemac.metamac.notices.core.dto.NoticeCreationResultDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;

@GenDispatch(isSecure = false)
public class CreateNoticeForExternalUsers {

    @In(1)
    NoticeDto               notice;

    @Out(1)
    NoticeCreationResultDto noticeCreationResultDto;
}
