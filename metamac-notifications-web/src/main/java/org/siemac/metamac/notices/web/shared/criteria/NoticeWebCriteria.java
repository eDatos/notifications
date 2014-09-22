package org.siemac.metamac.notices.web.shared.criteria;

import static org.siemac.metamac.notices.web.client.utils.NoticesWebConstants.NOTICES_LIST_MAX_RESULTS;

import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

public class NoticeWebCriteria extends PaginationWebCriteria {

    private static final long serialVersionUID = 3266436132524273569L;

    private boolean           acknowledge;
    private String            senginApplication;
    private String            sendingUser;
    private NoticeType        type;
    private String            receiverUsername;

    public NoticeWebCriteria() {
        setFirstResult(0);
        setMaxResults(NOTICES_LIST_MAX_RESULTS);
    }

    public boolean isAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    public String getSenginApplication() {
        return senginApplication;
    }

    public void setSenginApplication(String senginApplication) {
        this.senginApplication = senginApplication;
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

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
}
