package org.siemac.metamac.notices.web.client.utils;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getCoreMessages;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.web.client.NoticesWeb;
import org.siemac.metamac.notices.web.client.enums.ReceiverType;
import org.siemac.metamac.notices.web.shared.dto.AccessControlValueDto;
import org.siemac.metamac.notices.web.shared.utils.CommonSharedUtils;

public class CommonUtils {

    public static boolean isRead(NoticeDto noticeDto) {
        ReceiverDto receiverDto = getCurrentReceiverFromNotice(noticeDto);
        return receiverDto.isAcknowledge();
    }

    public static ReceiverDto getCurrentReceiverFromNotice(NoticeDto noticeDto) {
        String currentUsername = NoticesWeb.getCurrentUser().getUserId();
        return CommonSharedUtils.getCurrentReceiverFromNotice(noticeDto, currentUsername);
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

    public static ReceiverType getReceiverType(String type) {
        try {
            return ReceiverType.valueOf(type);
        } catch (Exception e) {
        }
        return null;
    }

    public static LinkedHashMap<String, String> getReceiverTypeLinkedHashMap() {
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        valueMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
        valueMap.put(ReceiverType.USERS.name(), NoticesWeb.getConstants().receiverTypeUsernames());
        valueMap.put(ReceiverType.CONDITIONS.name(), NoticesWeb.getConstants().receiverTypeConditions());
        return valueMap;
    }

    public static List<String> getAccessControlValueCodes(List<AccessControlValueDto> values) {
        List<String> codes = new ArrayList<String>(values.size());
        for (AccessControlValueDto value : values) {
            codes.add(value.getCode());
        }
        return codes;
    }
}
