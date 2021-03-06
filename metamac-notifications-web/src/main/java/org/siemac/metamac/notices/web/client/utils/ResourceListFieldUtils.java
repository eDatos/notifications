package org.siemac.metamac.notices.web.client.utils;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.web.client.model.ds.NoticeDS;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

public class ResourceListFieldUtils {

    public static CustomListGridField[] getNoticeFields() {

        CustomListGridField subject = new CustomListGridField(NoticeDS.SUBJECT, getConstants().noticeSubject());
        CustomListGridField type = new CustomListGridField(NoticeDS.TYPE, getConstants().noticeType());
        type.setWidth("10%");
        CustomListGridField sendingApplication = new CustomListGridField(NoticeDS.SENDING_APPLICATION, getConstants().noticeSendingApplication());
        sendingApplication.setWidth("20%");
        CustomListGridField sendingUser = new CustomListGridField(NoticeDS.SENDING_USER, getConstants().noticeSendingUser());
        sendingUser.setWidth("10%");
        CustomListGridField creationDate = new CustomListGridField(NoticeDS.CREATION_DATE, getConstants().noticeCreationDate());
        creationDate.setWidth("10%");

        CustomListGridField expirationDate = new CustomListGridField(NoticeDS.EXPIRATION_DATE, getConstants().noticeExpirationDate());
        expirationDate.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());
        expirationDate.setWidth("10%");

        CustomListGridField urn = new CustomListGridField(NoticeDS.URN, getConstants().noticeURN());
        urn.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());

        return new CustomListGridField[]{subject, type, sendingApplication, sendingUser, creationDate, expirationDate, urn};
    }
}
