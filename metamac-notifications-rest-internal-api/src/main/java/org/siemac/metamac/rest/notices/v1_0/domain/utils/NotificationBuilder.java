package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import java.util.Date;

import org.siemac.metamac.rest.notifications.v1_0.domain.Applications;
import org.siemac.metamac.rest.notifications.v1_0.domain.Messages;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationType;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;
import org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperations;

public class NotificationBuilder extends NotificationBuilderBase<NotificationBuilder> {

    public static NotificationBuilder notification() {
        return new NotificationBuilder();
    }

    public NotificationBuilder() {
        super(new Notification());
    }

    public Notification build() {
        return getInstance();
    }
}

class NotificationBuilderBase<GeneratorT extends NotificationBuilderBase<GeneratorT>> {

    private final Notification instance;

    protected NotificationBuilderBase(Notification aInstance) {
        instance = aInstance;
    }

    protected Notification getInstance() {
        return instance;
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
    public GeneratorT withSendingDate(Date aValue) {
        instance.setSendingDate(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withNotificationType(NotificationType aValue) {
        instance.setNotificationType(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withExpirationDate(Date aValue) {
        instance.setExpirationDate(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withMessages(Messages aValue) {
        instance.setMessages(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withRoles(Roles aValue) {
        instance.setRoles(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withApplications(Applications aValue) {
        instance.setApplications(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withStatisticalOperations(StatisticalOperations aValue) {
        instance.setStatisticalOperations(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withReceivers(Receivers aValue) {
        instance.setReceivers(aValue);

        return (GeneratorT) this;
    }
}
