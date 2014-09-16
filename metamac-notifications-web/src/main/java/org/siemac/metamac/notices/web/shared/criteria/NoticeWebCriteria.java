package org.siemac.metamac.notices.web.shared.criteria;

import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class NoticeWebCriteria extends MetamacWebCriteria {

    private static final long serialVersionUID = 3266436132524273569L;

    private boolean           read;
    private String            senginApplication;
    private String            sendingUser;
    private NoticeType        type;
    private String            receiverUsername;
    private int               firstResult;
    private int               maxResults;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
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

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
