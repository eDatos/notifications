package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import java.util.Date;

import org.siemac.metamac.rest.notices.v1_0.domain.Applications;
import org.siemac.metamac.rest.notices.v1_0.domain.Messages;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.NoticeType;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.Roles;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperations;

public class NoticeBuilder extends NoticeBuilderBase<NoticeBuilder> {

    public static NoticeBuilder notice() {
        return new NoticeBuilder();
    }

    public NoticeBuilder() {
        super(new Notice());
    }

    public Notice build() {
        return getInstance();
    }
}

class NoticeBuilderBase<GeneratorT extends NoticeBuilderBase<GeneratorT>> {

    private final Notice instance;

    protected NoticeBuilderBase(Notice aInstance) {
        instance = aInstance;
    }

    protected Notice getInstance() {
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
    public GeneratorT withNoticeType(NoticeType aValue) {
        instance.setNoticeType(aValue);

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
