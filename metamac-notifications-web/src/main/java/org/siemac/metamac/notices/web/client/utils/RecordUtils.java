package org.siemac.metamac.notices.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.web.client.model.NoticeRecord;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RecordUtils {

    public static NoticeRecord[] getNoticesRecords(List<NoticeDto> notices) {
        List<NoticeRecord> records = new ArrayList<NoticeRecord>();
        for (NoticeDto noticeDto : notices) {
            records.add(getNoticeRecord(noticeDto));
        }
        return records.toArray(new NoticeRecord[records.size()]);
    }

    public static NoticeRecord getNoticeRecord(NoticeDto noticeDto) {
        ReceiverDto receiverDto = CommonUtils.getCurrentReceiverFromNotice(noticeDto);
        return getNoticeRecord(noticeDto, receiverDto);
    }

    public static NoticeRecord getNoticeRecord(NoticeDto noticeDto, ReceiverDto receiverDto) {
        NoticeRecord record = new NoticeRecord();
        record.setNoticeId(noticeDto.getId());
        record.setUrn(noticeDto.getUrn());
        record.setSendingApplication(noticeDto.getSendingApplication());
        record.setSendingUser(noticeDto.getSendingUser());
        record.setExpirationDate(noticeDto.getExpirationDate());
        record.setSubject(noticeDto.getSubject());
        record.setType(noticeDto.getType());
        record.setCreationDate(noticeDto.getCreationDate());
        record.setNoticeDto(noticeDto);
        if (receiverDto != null) {
            record.setReceiverId(receiverDto.getId());
            record.setReceiverUsername(receiverDto.getUsername());
            record.setReceiverAcknowledge(receiverDto.isAcknowledge());
        }
        return record;
    }

    public static List<NoticeDto> getNoticeDtos(ListGridRecord[] records) {
        List<NoticeDto> notices = new ArrayList<NoticeDto>();
        for (ListGridRecord record : records) {
            if (record instanceof NoticeRecord) {
                notices.add(((NoticeRecord) record).getNoticeDto());
            }
        }
        return notices;
    }
}
