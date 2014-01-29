package org.siemac.metamac.rest.notices.v1_0.utils;

import java.util.Date;

import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessagesUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.ReceiversUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.RolesUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.StatisticalOperationsUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.Messages;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.NoticeType;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.Roles;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperations;

public class NoticesRestMocks {

    public static Notice mockNotice_TYPE_NOTIFICATION() {
        Notice notice = new Notice();
        notice.setNoticeType(NoticeType.NOTIFICATION);

        notice.setSendingApplication("application");
        notice.setSendingUser("user");
        notice.setExpirationDate(new Date());
        notice.setSubject("My subject");

        // Roles
        Roles roles = RolesUtils.createRolesList(MetamacRolesEnum.ADMINISTRADOR);
        notice.setRoles(roles);

        // Statistical operations
        StatisticalOperations statisticalOperations = StatisticalOperationsUtils.createStatisticalOperationsList("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=operation01");
        notice.setStatisticalOperations(statisticalOperations);

        // Receivers
        Receivers receivers = ReceiversUtils.createReceiversList("user-1", "user-2", "user-3", "user-4", "user-5");
        notice.setReceivers(receivers);

        // Messages
        Messages messages = MessagesUtils.createMessagesList("My message");
        notice.setMessages(messages);

        return notice;
    }

    public static Notice mockNotice_TYPE_NOTIFICATION(String urn) {
        Notice notice = mockNotice_TYPE_NOTIFICATION();
        notice.setUrn(urn);

        return notice;
    }

}