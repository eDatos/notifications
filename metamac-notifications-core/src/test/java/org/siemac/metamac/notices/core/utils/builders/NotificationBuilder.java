package org.siemac.metamac.notices.core.utils.builders;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.notices.core.notice.domain.App;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notification;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;
import org.siemac.metamac.notices.core.notice.enume.domain.NotificationType;
import org.siemac.metamac.notices.core.utils.mocks.templates.NotificationsDoMocks;

public class NotificationBuilder extends NotificationBuilderBase<NotificationBuilder> {

    // By default we generate mock values for required-in-creation fields
    // This lets a simple interface for the builder

    public static NotificationBuilder notification() {
        String sendingApplication = NotificationsDoMocks.mockString(8);
        String subject = NotificationsDoMocks.mockSentence(5);
        NotificationType notificationType = NotificationType.NOTIFICATION;
        return new NotificationBuilder(sendingApplication, subject, notificationType);
    }

    public static NotificationBuilder advertisement() {
        String sendingApplication = NotificationsDoMocks.mockString(8);
        String subject = NotificationsDoMocks.mockSentence(5);
        NotificationType notificationType = NotificationType.ADVERTISEMENT;
        return new NotificationBuilder(sendingApplication, subject, notificationType);
    }

    public NotificationBuilder(String sendingApplication, String subject, NotificationType notificationType) {
        super(new Notification(sendingApplication, subject, notificationType));
    }

    public Notification build() {
        return getInstance();
    }
}

class NotificationBuilderBase<GeneratorT extends NotificationBuilderBase<GeneratorT>> {

    private final Notification instance;

    @SuppressWarnings("unchecked")
    public GeneratorT fillEmptyAuditableFields() {
        if (instance.getCreatedDate() == null) {
            instance.setCreatedDate(new DateTime(2012, 1, 1, 1, 1, 1, 1));
        }

        if (StringUtils.isBlank(instance.getCreatedBy())) {
            instance.setCreatedBy("TEST-USER");
        }

        if (instance.getLastUpdated() == null) {
            instance.setLastUpdated(new DateTime(2012, 1, 1, 1, 1, 1, 1));
        }

        if (StringUtils.isBlank(instance.getLastUpdatedBy())) {
            instance.setLastUpdatedBy("TEST-USER");
        }

        return (GeneratorT) this;
    }

    protected NotificationBuilderBase(Notification aInstance) {
        instance = aInstance;
    }

    protected Notification getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withUrn(String aValue) {
        instance.setUrn(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withSendingApplication(String aValue) {
        instance.setSendingApplication(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withSendingUser(String aValue) {
        instance.setSendingUser(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withExpirationDate(DateTime aValue) {
        instance.setExpirationDate(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withMark(boolean aValue) {
        instance.setMark(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withSubject(String aValue) {
        instance.setSubject(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withCreatedDate(DateTime aValue) {
        instance.setCreatedDate(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withCreatedBy(String aValue) {
        instance.setCreatedBy(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withLastUpdated(DateTime aValue) {
        instance.setLastUpdated(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withLastUpdatedBy(String aValue) {
        instance.setLastUpdatedBy(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withVersion(Long aValue) {
        instance.setVersion(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withNotificationType(NotificationType aValue) {
        instance.setNotificationType(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedMessage(String aValue) {
        Message message = MessageBuilder.message().withText(aValue).build();
        instance.getMessages().add(message);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withMessages(String... aValue) {
        instance.getMessages().clear();
        for (String messageText : aValue) {
            Message message = MessageBuilder.message().withText(messageText).build();
            instance.getMessages().add(message);
        }

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedMessage(Message aValue) {
        instance.getMessages().add(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withMessages(Message... aValue) {
        instance.getMessages().clear();
        for (Message message : aValue) {
            instance.getMessages().add(message);
        }

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedStatisticalOperation(String aValue) {
        StatisticalOperation statisticalOperation = StatisticalOperationBuilder.statisticalOperation().withName(aValue).build();
        instance.getStatisticalOperations().add(statisticalOperation);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withStatisticalOperations(String... aValue) {
        instance.getStatisticalOperations().clear();
        for (String statisticalOperationName : aValue) {
            StatisticalOperation statisticalOperation = StatisticalOperationBuilder.statisticalOperation().withName(statisticalOperationName).build();
            instance.getStatisticalOperations().add(statisticalOperation);
        }

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedApp(String aValue) {
        App app = AppBuilder.app().withName(aValue).build();
        instance.getApps().add(app);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withApps(String... aValue) {
        instance.getApps().clear();
        for (String appName : aValue) {
            App app = AppBuilder.app().withName(appName).build();
            instance.getApps().add(app);
        }

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedRole(String aValue) {
        Role role = RoleBuilder.role().withName(aValue).build();
        instance.getRoles().add(role);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withRoles(String... aValue) {
        instance.getRoles().clear();
        for (String roleName : aValue) {
            Role role = RoleBuilder.role().withName(roleName).build();
            instance.getRoles().add(role);
        }

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedReceiver(String aValue) {
        Receiver receiver = ReceiverBuilder.receiver().withUsername(aValue).build();
        instance.getReceivers().add(receiver);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withReceivers(String... aValue) {
        instance.getReceivers().clear();
        for (String receiverUsername : aValue) {
            Receiver receiver = ReceiverBuilder.receiver().withUsername(receiverUsername).build();
            instance.getReceivers().add(receiver);
        }
        return (GeneratorT) this;
    }

}
