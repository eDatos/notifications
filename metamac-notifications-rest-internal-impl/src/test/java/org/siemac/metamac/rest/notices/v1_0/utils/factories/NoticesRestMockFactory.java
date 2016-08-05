package org.siemac.metamac.rest.notices.v1_0.utils.factories;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.siemac.metamac.rest.notices.v1_0.utils.templates.CommonRestMocks;

public class NoticesRestMockFactory {

    public static Notice getNotification01Basic() {
        Notice notice = NoticeBuilder.notification().withSendingUser("user").withSendingApplication("application").withSubject("My subject")
                .withStatisticalOperations("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=operation01").withReceivers("user-1", "user-2", "user-3", "user-4", "user-5")
                .withMessagesWithoutResources("My message").withRoles(MetamacRolesEnum.ADMINISTRADOR).build();
        return notice;
    }

    public static Notice getNotification02WithMultiplesMessages() {
        Notice notice = NoticeBuilder.notification().withSendingUser("user").withSendingApplication("application").withSubject("My subject")
                .withStatisticalOperations("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=operation01").withReceivers("user-1", "user-2", "user-3", "user-4", "user-5")
                .withMessagesWithoutResources("My message 01", "My message 02").withRoles(MetamacRolesEnum.ADMINISTRADOR).build();
        return notice;
    }

    public static Notice getNotification03WithMultiplesMessagesAndResources() {
        Message message01 = MessageBuilder.message().withText("Message without resources").build();
        Message message02 = MessageBuilder
                .message()
                .withText("Message with resources")
                .withResources(CommonRestMocks.mockResourceFromExternalItemSrm("RESOURCE_01", "resource", TypeExternalArtefactsEnum.CATEGORY.getValue()),
                        CommonRestMocks.mockResourceFromExternalItemSrm("RESOURCE_02", "resource", TypeExternalArtefactsEnum.CONCEPT.getValue())).build();

        Notice notice = NoticeBuilder.notification().withSendingUser("user").withSendingApplication("application").withSubject("My subject")
                .withStatisticalOperations("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=operation01").withReceivers("user-1", "user-2", "user-3", "user-4", "user-5")
                .withMessages(message01, message02).withRoles(MetamacRolesEnum.ADMINISTRADOR).build();
        return notice;
    }
}
