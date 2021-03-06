package org.siemac.metamac.notices.web.client.model;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getCoreMessages;

import java.util.Date;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.notices.web.client.utils.AccessControlValues;
import org.siemac.metamac.web.common.client.utils.DateUtils;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class NoticeRecord extends ListGridRecord {

    public void setNoticeId(Long value) {
        setAttribute(NoticeDS.NOTICE_ID, value);
    }

    public void setReceiverId(Long value) {
        setAttribute(NoticeDS.RECEIVER_ID, value);
    }

    public void setUrn(String value) {
        setAttribute(NoticeDS.URN, value);
    }

    public String getUrn() {
        return getAttributeAsString(NoticeDS.URN);
    }

    public void setSendingApplication(String value) {
        String appTitle = AccessControlValues.getAppTitle(value);
        setAttribute(NoticeDS.SENDING_APPLICATION, appTitle);
    }

    public void setSendingUser(String value) {
        setAttribute(NoticeDS.SENDING_USER, value);
    }

    public void setExpirationDate(Date value) {
        setAttribute(NoticeDS.EXPIRATION_DATE, DateUtils.getFormattedDate(value));
    }

    public void setSubject(String value) {
        setAttribute(NoticeDS.SUBJECT, value);
    }

    public void setType(NoticeType type) {
        if (type != null) {
            setAttribute(NoticeDS.TYPE, getCoreMessages().getString(getCoreMessages().noticeType() + type.name()));
        }
    }

    public void setReceiverUsername(String value) {
        setAttribute(NoticeDS.RECEIVER_USERNAME, value);
    }

    public void setReceiverAcknowledge(boolean value) {
        setAttribute(NoticeDS.RECEIVER_ACKNOWLEDGE, value);
    }

    public boolean getReceiverAcknowledge() {
        return getAttributeAsBoolean(NoticeDS.RECEIVER_ACKNOWLEDGE);
    }

    public void setCreationDate(Date value) {
        setAttribute(NoticeDS.CREATION_DATE, DateUtils.getFormattedDateTime(value));
    }

    public void setNoticeDto(NoticeDto value) {
        setAttribute(NoticeDS.DTO, value);
    }

    public NoticeDto getNoticeDto() {
        return (NoticeDto) getAttributeAsObject(NoticeDS.DTO);
    }
}
