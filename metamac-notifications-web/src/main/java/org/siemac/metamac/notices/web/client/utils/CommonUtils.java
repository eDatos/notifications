package org.siemac.metamac.notices.web.client.utils;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.web.client.NoticesWeb;

public class CommonUtils {

    public static ReceiverDto getCurrentReceiverFromNotice(NoticeDto noticeDto) {
        String currentUsername = NoticesWeb.getCurrentUser().getUserId();
        for (ReceiverDto receiverDto : noticeDto.getReceivers()) {
            if (StringUtils.equals(currentUsername, receiverDto.getUsername())) {
                return receiverDto;
            }
        }
        return null;
    }
}
