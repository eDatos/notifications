package org.siemac.metamac.notices.core.notice.domain;

import java.util.HashSet;
import java.util.Set;

public class NoticeCreationResult {

    private Notice      notice;
    private Set<String> receiversUsernamesWithError = new HashSet<String>();

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Set<String> getReceiversUsernamesWithError() {
        return receiversUsernamesWithError;
    }

    public void setReceiversUsernamesWithError(Set<String> receiversUsernamesWithError) {
        this.receiversUsernamesWithError = receiversUsernamesWithError;
    }
}
