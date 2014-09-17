package org.siemac.metamac.notices.web.client.utils;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

public class ResourceListFieldUtils {

    public static CustomListGridField[] getNoticeFields() {

        CustomListGridField subject = new CustomListGridField(NoticeDS.SUBJECT, getConstants().noticeSubject());
        CustomListGridField type = new CustomListGridField(NoticeDS.TYPE, getConstants().noticeType());
        CustomListGridField sendingApplication = new CustomListGridField(NoticeDS.SENDING_APPLICATION, getConstants().noticeSendingApplication());
        CustomListGridField sendingUser = new CustomListGridField(NoticeDS.SENDING_USER, getConstants().noticeSendingUser());

        CustomListGridField expirationDate = new CustomListGridField(NoticeDS.EXPIRATION_DATE, getConstants().noticeExpirationDate());
        expirationDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        CustomListGridField urn = new CustomListGridField(NoticeDS.URN, getConstants().noticeURN());
        urn.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        return new CustomListGridField[]{subject, type, sendingApplication, sendingUser, expirationDate, urn};
    }
}
