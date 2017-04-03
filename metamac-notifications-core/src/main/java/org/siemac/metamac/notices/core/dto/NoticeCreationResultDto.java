package org.siemac.metamac.notices.core.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class NoticeCreationResultDto implements Serializable {

    private static final long serialVersionUID            = 8625176534010948336L;

    private NoticeDto         noticeDto;
    private Set<String>       receiversUsernamesWithError = new HashSet<String>();

    public NoticeDto getNoticeDto() {
        return noticeDto;
    }

    public void setNoticeDto(NoticeDto noticeDto) {
        this.noticeDto = noticeDto;
    }

    public Set<String> getReceiversUsernamesWithError() {
        return receiversUsernamesWithError;
    }

    public void setReceiversUsernamesWithError(Set<String> receiversUsernamesWithError) {
        this.receiversUsernamesWithError = receiversUsernamesWithError;
    }
}
