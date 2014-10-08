package org.siemac.metamac.notices.web.shared;

import org.siemac.metamac.notices.core.dto.NoticeDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetNotice {

    @In(1)
    String    noticeUrn;

    @Out(1)
    NoticeDto updatedNotice;
}
