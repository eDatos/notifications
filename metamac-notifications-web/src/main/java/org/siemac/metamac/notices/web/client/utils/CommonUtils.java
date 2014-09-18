package org.siemac.metamac.notices.web.client.utils;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getCoreMessages;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
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

    public static String getNoticeTypeName(NoticeType type) {
        if (type == null) {
            return StringUtils.EMPTY;
        }
        return getCoreMessages().getString(getCoreMessages().noticeType() + type.name());
    }

    public static NoticeType getNoticeType(String type) {
        try {
            return NoticeType.valueOf(type);
        } catch (Exception e) {
        }
        return null;
    }
}
