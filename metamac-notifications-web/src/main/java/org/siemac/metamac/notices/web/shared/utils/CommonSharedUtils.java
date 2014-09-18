package org.siemac.metamac.notices.web.shared.utils;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;

public class CommonSharedUtils {

    public static ReceiverDto getCurrentReceiverFromNotice(NoticeDto noticeDto, String username) {
        for (ReceiverDto receiverDto : noticeDto.getReceivers()) {
            if (StringUtils.equals(username, receiverDto.getUsername())) {
                return receiverDto;
            }
        }
        return null;
    }
}
