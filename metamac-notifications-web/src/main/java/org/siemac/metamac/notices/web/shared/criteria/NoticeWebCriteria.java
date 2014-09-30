package org.siemac.metamac.notices.web.shared.criteria;

import static org.siemac.metamac.notices.web.client.utils.NoticesWebConstants.NOTICES_LIST_MAX_RESULTS;

import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

public class NoticeWebCriteria extends PaginationWebCriteria {

    private static final long serialVersionUID = 3266436132524273569L;

    private String            receiverUsername;
    private Boolean           acknowledge;
    private String            sendingApplication;
    private String            sendingUser;
    private NoticeType        type;

    public NoticeWebCriteria() {
        setFirstResult(0);
        setMaxResults(NOTICES_LIST_MAX_RESULTS);
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public Boolean getAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(Boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    public String getSendingApplication() {
        return sendingApplication;
    }

    public void setSendingApplication(String sendingApplication) {
        this.sendingApplication = sendingApplication;
    }

    public String getSendingUser() {
        return sendingUser;
    }

    public void setSendingUser(String sendingUser) {
        this.sendingUser = sendingUser;
    }

    public NoticeType getType() {
        return type;
    }

    public void setType(NoticeType type) {
        this.type = type;
    }
}
