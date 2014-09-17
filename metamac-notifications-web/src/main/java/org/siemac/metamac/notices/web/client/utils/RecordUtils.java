package org.siemac.metamac.notices.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.web.client.model.NoticeRecord;

public class RecordUtils {

    public static NoticeRecord[] getNoticesRecords(List<NoticeDto> notices) {
        List<NoticeRecord> records = new ArrayList<NoticeRecord>();
        for (NoticeDto noticeDto : notices) {
            records.addAll(getNoticesRecord(noticeDto));
        }
        return records.toArray(new NoticeRecord[records.size()]);
    }

    public static List<NoticeRecord> getNoticesRecord(NoticeDto noticeDto) {
        List<NoticeRecord> records = new ArrayList<NoticeRecord>();
        for (ReceiverDto receiverDto : noticeDto.getReceivers()) {
            records.add(getNoticeRecord(noticeDto, receiverDto));
        }
        return records;
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
        record.setReceiverId(receiverDto.getId());
        record.setReceiverUsername(receiverDto.getUsername());
        record.setReceiverAcknowledge(receiverDto.isAcknowledge());
        return record;
    }
}
