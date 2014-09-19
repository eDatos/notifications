package org.siemac.metamac.notices.web.shared;

import org.siemac.metamac.notices.core.dto.NoticeDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class CreateNotice {

    @In(1)
    NoticeDto notice;

    @Out(1)
    NoticeDto createdNotice;
}
