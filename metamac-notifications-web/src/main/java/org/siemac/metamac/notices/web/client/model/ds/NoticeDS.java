package org.siemac.metamac.notices.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class NoticeDS extends DataSource {

    public static final String NOTICE_ID             = "notice-id";
    public static final String RECEIVER_ID           = "notice-receiver-id";
    public static final String URN                   = "notice-urn";
    public static final String SENDING_APPLICATION   = "notice-send-app";
    public static final String SENDING_USER          = "notice-send-user";
    public static final String EXPIRATION_DATE       = "notice-exp-date";
    public static final String SUBJECT               = "notice-subject";
    public static final String TYPE                  = "notice-type";
    public static final String TYPE_ENUM             = "notice-type-enum";
    public static final String RECEIVER_USERNAME     = "notice-rec-username";
    public static final String RECEIVER_ACKNOWLEDGE  = "notice-rec-acknowledge";
    public static final String MESSAGE               = "notice-message";
    public static final String CREATION_DATE         = "notice-creation-date";
    public static final String STATISTICAL_OPERATION = "notice-sta-operation";
    public static final String APPLICATION           = "notice-application";
    public static final String ROLE                  = "notice-role";

    public static final String DTO                   = "notice-dto";

    public NoticeDS() {
        DataSourceTextField noticeId = new DataSourceTextField(NOTICE_ID);
        noticeId.setPrimaryKey(true);
        addField(noticeId);

        DataSourceTextField receiverId = new DataSourceTextField(RECEIVER_ID);
        receiverId.setPrimaryKey(true);
        addField(receiverId);
    }
}
