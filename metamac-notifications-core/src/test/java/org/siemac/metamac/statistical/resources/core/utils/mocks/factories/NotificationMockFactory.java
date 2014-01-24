package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.core.common.test.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.common.domain.InternationalString;
import org.siemac.metamac.notifications.core.notice.domain.Message;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.utils.builders.MessageBuilder;
import org.siemac.metamac.notifications.core.utils.builders.NotificationBuilder;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.NotificationsDoMocks;

@MockProvider
public class NotificationMockFactory extends MetamacNotificationsMockFactory<Notification> {

    public static final String             NOTIFICATION_01_WITH_CONDITIONS_NAME = "NOTIFICATION_01_WITH_CONDITIONS";
    public static final String             NOTIFICATION_01_URN                  = "urn:siemac:org.siemac.metamac.infomodel.notices.Notification=NOTIFICATION_01_WITH_CONDITIONS";

    public static final String             NOTIFICATION_02_WITH_RECEIVERS_NAME  = "NOTIFICATION_02_WITH_RECEIVERS";
    public static final String             NOTIFICATION_02_URN                  = "urn:siemac:org.siemac.metamac.infomodel.notices.Notification=NOTIFICATION_02_WITH_RECEIVERS";

    public static final String             NOTIFICATION_03_WITH_RESOURCES_NAME  = "NOTIFICATION_03_WITH_RESOURCES";
    public static final String             NOTIFICATION_03_URN                  = "urn:siemac:org.siemac.metamac.infomodel.notices.Notification=NOTIFICATION_03_WITH_RESOURCES";

    private static NotificationMockFactory instance                             = null;

    private NotificationMockFactory() {
    }

    public static NotificationMockFactory getInstance() {
        if (instance == null) {
            instance = new NotificationMockFactory();
        }
        return instance;
    }

    public static Notification getNotification01WithConditions() {
        Notification notification = NotificationBuilder.notification().withSendingApplication("TEST-APPLICATION").withSubject("Test subject").withUrn(NOTIFICATION_01_URN)
                .withAddedMessage("Test message").withApps("APP01", "APP02").withRoles("ROLE01").withStatisticalOperations("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=C00025A", "urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=C00025B").fillEmptyAuditableFields().build();
        return notification;
    }

    public static Notification getNotification02WithReceivers() {
        Notification notification = NotificationBuilder.notification().withSendingApplication("TEST-APPLICATION").withSubject("Test subject").withUrn(NOTIFICATION_02_URN)
                .withAddedMessage("Test message").withReceivers("user-1", "user-2", "user-3", "user-4", "user-5").fillEmptyAuditableFields().build();
        return notification;
    }

    public static Notification getNotification03WithResources() {
        ExternalItem resource01 = NotificationsDoMocks.mockConceptExternalItem("TEST-CONCEPT");
        resource01.setTitle(new InternationalString("en", "Test concept"));

        ExternalItem resource02 = NotificationsDoMocks.mockAgencyExternalItem("TEST-AGENCY");
        resource02.setTitle(new InternationalString("en", "Test agency"));

        Message message01 = MessageBuilder.message().withText("Message 01: with resources").withResources(resource01, resource02).build();

        Message message02 = MessageBuilder.message().withText("Message 02: without resources").build();

        Notification notification = NotificationBuilder.notification().withSendingApplication("TEST-APPLICATION").withSubject("Test subject").withUrn(NOTIFICATION_03_URN)
                .withReceivers("user-1", "user-2", "user-3", "user-4", "user-5").withMessages(message01, message02).fillEmptyAuditableFields().build();
        return notification;
    }

}