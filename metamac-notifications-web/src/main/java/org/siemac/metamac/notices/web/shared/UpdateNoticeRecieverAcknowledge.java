package org.siemac.metamac.notices.web.shared;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class UpdateNoticeRecieverAcknowledge {

    @In(1)
    List<NoticeDto> notices;

    @In(2)
    boolean         acknowledgeStatus;
}
