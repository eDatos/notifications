package org.siemac.metamac.notices.core.utils.mocks.factories;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.common.domain.InternationalString;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.utils.builders.MessageBuilder;
import org.siemac.metamac.notices.core.utils.builders.NoticeBuilder;
import org.siemac.metamac.notices.core.utils.mocks.templates.NoticesDoMocks;

@MockProvider
public class NoticeMockFactory extends MetamacNoticesMockFactory<Notice> {

    public static final String       NOTIFICATION_01_WITH_CONDITIONS_NAME = "NOTIFICATION_01_WITH_CONDITIONS";
    public static final String       NOTIFICATION_01_URN                  = "urn:siemac:org.siemac.metamac.infomodel.notices.Notice=NOTIFICATION_01_WITH_CONDITIONS";

    public static final String       NOTIFICATION_02_WITH_RECEIVERS_NAME  = "NOTIFICATION_02_WITH_RECEIVERS";
    public static final String       NOTIFICATION_02_URN                  = "urn:siemac:org.siemac.metamac.infomodel.notices.Notice=NOTIFICATION_02_WITH_RECEIVERS";

    public static final String       NOTIFICATION_03_WITH_RESOURCES_NAME  = "NOTIFICATION_03_WITH_RESOURCES";
    public static final String       NOTIFICATION_03_URN                  = "urn:siemac:org.siemac.metamac.infomodel.notices.Notice=NOTIFICATION_03_WITH_RESOURCES";

    private static NoticeMockFactory instance                             = null;

    private NoticeMockFactory() {
    }

    public static NoticeMockFactory getInstance() {
        if (instance == null) {
            instance = new NoticeMockFactory();
        }
        return instance;
    }

    public static Notice getNotice01WithConditions() {
        Notice notice = NoticeBuilder
                .notification()
                .withSendingApplication("TEST-APPLICATION")
                .withSubject("Test subject")
                .withUrn(NOTIFICATION_01_URN)
                .withAddedMessage("Test message")
                .withApps("APP01", "APP02")
                .withRoles("ROLE01")
                .withStatisticalOperations("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=C00025A",
                        "urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=C00025B").fillEmptyAuditableFields().build();
        return notice;
    }

    public static Notice getNotice02WithReceivers() {
        Notice notice = NoticeBuilder.notification().withSendingApplication("TEST-APPLICATION").withSubject("Test subject").withUrn(NOTIFICATION_02_URN).withAddedMessage("Test message")
                .withReceivers("user-1", "user-2", "user-3", "user-4", "user-5").fillEmptyAuditableFields().build();
        return notice;
    }

    public static Notice getNotice03WithResources() {
        ExternalItem resource01 = NoticesDoMocks.mockConceptExternalItem("TEST-CONCEPT");
        resource01.setTitle(new InternationalString(new String[]{"en", "es"}, (new String[]{"Test concept", "Test concepto"})));

        ExternalItem resource02 = NoticesDoMocks.mockAgencyExternalItem("TEST-AGENCY");
        resource02.setTitle(new InternationalString(new String[]{"en", "es"}, (new String[]{"Test agency", "Test agencia"})));

        Message message01 = MessageBuilder.message().withText("Message 01: with resources").withResources(resource01, resource02).build();

        Message message02 = MessageBuilder.message().withText("Message 02: without resources").build();

        Notice notice = NoticeBuilder.notification().withSendingApplication("TEST-APPLICATION").withSubject("Test subject").withUrn(NOTIFICATION_03_URN)
                .withReceivers("user-1", "user-2", "user-3", "user-4", "user-5").withMessages(message01, message02).fillEmptyAuditableFields().build();

        return notice;
    }

}