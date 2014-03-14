package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import java.math.BigInteger;
import java.util.Date;

import org.siemac.metamac.rest.notices.v1_0.domain.Application;
import org.siemac.metamac.rest.notices.v1_0.domain.Applications;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Messages;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.NoticeType;
import org.siemac.metamac.rest.notices.v1_0.domain.Receiver;
import org.siemac.metamac.rest.notices.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notices.v1_0.domain.Role;
import org.siemac.metamac.rest.notices.v1_0.domain.Roles;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperation;
import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperations;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;

public class NoticeBuilder extends NoticeBuilderBase<NoticeBuilder> {

    public static NoticeBuilder notification() {
        NoticeBuilder noticeBuilder = new NoticeBuilder();
        noticeBuilder.getInstance().setNoticeType(NoticeType.NOTIFICATION);
        return noticeBuilder;
    }

    public static NoticeBuilder announcement() {
        NoticeBuilder noticeBuilder = new NoticeBuilder();
        noticeBuilder.getInstance().setNoticeType(NoticeType.ANNOUNCEMENT);
        return noticeBuilder;
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
    public GeneratorT withSubject(String aValue) {
        instance.setSubject(aValue);

        return (GeneratorT) this;
    }

    public GeneratorT withMessagesWithoutResources(String... aValue) {
        Messages messages = new Messages();
        for (String messageText : aValue) {
            Message message = new Message();
            message.setText(messageText);
            messages.getMessages().add(message);
        }
        messages.setTotal(BigInteger.valueOf(aValue.length));

        return withMessages(messages);
    }

    public GeneratorT withMessages(Message... aValue) {
        Messages messages = new Messages();
        for (Message message : aValue) {
            messages.getMessages().add(message);
        }
        messages.setTotal(BigInteger.valueOf(aValue.length));

        return withMessages(messages);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withMessages(Messages aValue) {
        instance.setMessages(aValue);

        return (GeneratorT) this;
    }

    public GeneratorT withRoles(MetamacRolesEnum... aValue) {
        Roles roles = new Roles();
        for (MetamacRolesEnum roleName : aValue) {
            Role role = RoleBuilder.role().withName(roleName.toString()).build();
            roles.getRoles().add(role);
        }
        roles.setTotal(BigInteger.valueOf(aValue.length));

        return withRoles(roles);
    }

    public GeneratorT withRoles(String... aValue) {
        Roles roles = new Roles();
        for (String roleName : aValue) {
            Role role = RoleBuilder.role().withName(roleName).build();
            roles.getRoles().add(role);
        }
        roles.setTotal(BigInteger.valueOf(aValue.length));

        return withRoles(roles);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withRoles(Roles aValue) {
        instance.setRoles(aValue);

        return (GeneratorT) this;
    }

    public GeneratorT withApplications(MetamacApplicationsEnum... aValue) {
        Applications applications = new Applications();
        for (MetamacApplicationsEnum applicationName : aValue) {
            Application application = ApplicationBuilder.application().withName(applicationName.toString()).build();
            applications.getApplications().add(application);
        }
        applications.setTotal(BigInteger.valueOf(aValue.length));

        return withApplications(applications);
    }

    public GeneratorT withApplications(String... aValue) {
        Applications applications = new Applications();
        for (String applicationName : aValue) {
            Application application = ApplicationBuilder.application().withName(applicationName).build();
            applications.getApplications().add(application);
        }
        applications.setTotal(BigInteger.valueOf(aValue.length));

        return withApplications(applications);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withApplications(Applications aValue) {
        instance.setApplications(aValue);

        return (GeneratorT) this;
    }

    public GeneratorT withStatisticalOperations(String... aValue) {
        StatisticalOperations statisticalOperations = new StatisticalOperations();
        for (String statisticalOperationUrn : aValue) {
            StatisticalOperation statisticalOperation = StatisticalOperationBuilder.statisticalOperation().withUrn(statisticalOperationUrn).build();
            statisticalOperations.getStatisticalOperations().add(statisticalOperation);
        }
        statisticalOperations.setTotal(BigInteger.valueOf(aValue.length));

        return withStatisticalOperations(statisticalOperations);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withStatisticalOperations(StatisticalOperations aValue) {
        instance.setStatisticalOperations(aValue);

        return (GeneratorT) this;
    }

    public GeneratorT withReceivers(String... aValue) {
        Receivers receivers = new Receivers();
        for (String receiverName : aValue) {
            Receiver receiver = ReceiverBuilder.receiver().withUsername(receiverName).build();
            receivers.getReceivers().add(receiver);
        }
        receivers.setTotal(BigInteger.valueOf(aValue.length));

        return withReceivers(receivers);
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withReceivers(Receivers aValue) {
        instance.setReceivers(aValue);

        return (GeneratorT) this;
    }
}
