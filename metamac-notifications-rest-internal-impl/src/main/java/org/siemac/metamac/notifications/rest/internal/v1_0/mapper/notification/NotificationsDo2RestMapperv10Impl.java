package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.notification;

import java.math.BigInteger;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.notice.domain.App;
import org.siemac.metamac.notifications.core.notice.domain.Message;
import org.siemac.metamac.notifications.core.notice.domain.Receiver;
import org.siemac.metamac.notifications.core.notice.domain.Role;
import org.siemac.metamac.notifications.core.notice.domain.StatisticalOperation;
import org.siemac.metamac.notifications.rest.internal.NotificationsRestConstants;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.notifications.v1_0.domain.Application;
import org.siemac.metamac.rest.notifications.v1_0.domain.Applications;
import org.siemac.metamac.rest.notifications.v1_0.domain.Messages;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationType;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;
import org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsDo2RestMapperv10Impl implements NotificationsDo2RestMapperV10 {

    @Autowired
    private CommonDo2RestMapperV10 commonDo2RestMapper;

    @Override
    public Notification notificationEntity2Rest(ServiceContext ctx, org.siemac.metamac.notifications.core.notice.domain.Notification source) throws MetamacException {
        if (source == null) {
            return null;
        }

        Notification target = new Notification();

        target.setKind(NotificationsRestConstants.KIND_NOTIFICATION);

        // TODO: Añadir mapper del campo ID
        // TODO: Añadir mapper del campo selfLink

        target.setUrn(source.getUrn());
        target.setSendingApplication(source.getSendingApplication());
        target.setSendingUser(source.getSendingUser());
        target.setSendingDate(CoreCommonUtil.transformDateTimeToDate(source.getCreatedDate()));
        target.setExpirationDate(CoreCommonUtil.transformDateTimeToDate(source.getExpirationDate()));
        target.setNotificationType(NotificationType.fromValue(source.getNotificationType().getName()));
        target.setSubject(source.getSubject());

        target.setMessages(messagesToRest(source.getMessages()));
        target.setReceivers(receiversToRest(source.getReceivers()));
        target.setApplications(applicationsToRest(source.getApps()));
        target.setRoles(rolesToRest(source.getRoles()));
        target.setStatisticalOperations(statisticalOperationsToRest(source.getStatisticalOperations()));

        return target;
    }

    private StatisticalOperations statisticalOperationsToRest(List<StatisticalOperation> source) {
        StatisticalOperations statisticalOperations = new StatisticalOperations();
        for (StatisticalOperation statisticalOperation : source) {
            org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperation targetStatisticalOperation = new org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperation();
            targetStatisticalOperation.setName(statisticalOperation.getName());
            statisticalOperations.getStatisticalOperations().add(targetStatisticalOperation);
        }
        if (statisticalOperations.getStatisticalOperations().size() != 0) {
            statisticalOperations.setTotal(new BigInteger(String.valueOf(statisticalOperations.getStatisticalOperations().size())));
        }

        return statisticalOperations;
    }

    private Roles rolesToRest(List<Role> source) {
        Roles roles = new Roles();
        for (Role role : source) {
            org.siemac.metamac.rest.notifications.v1_0.domain.Role targetRole = new org.siemac.metamac.rest.notifications.v1_0.domain.Role();
            targetRole.setName(role.getName());
            roles.getRoles().add(targetRole);
        }
        if (roles.getRoles().size() != 0) {
            roles.setTotal(new BigInteger(String.valueOf(roles.getRoles().size())));
        }

        return roles;
    }

    private Applications applicationsToRest(List<App> source) {
        Applications applications = new Applications();
        for (org.siemac.metamac.notifications.core.notice.domain.App app : source) {
            Application targetApplication = new Application();
            targetApplication.setName(app.getName());
            applications.getApplications().add(targetApplication);
        }
        if (applications.getApplications().size() != 0) {
            applications.setTotal(new BigInteger(String.valueOf(applications.getApplications().size())));
        }
        return applications;
    }

    private Receivers receiversToRest(List<Receiver> source) {
        Receivers receivers = new Receivers();
        for (org.siemac.metamac.notifications.core.notice.domain.Receiver sourceReceiver : source) {
            org.siemac.metamac.rest.notifications.v1_0.domain.Receiver targetReceiver = new org.siemac.metamac.rest.notifications.v1_0.domain.Receiver();
            targetReceiver.setUsername(sourceReceiver.getUsername());
            receivers.getReceivers().add(targetReceiver);
        }
        if (receivers.getReceivers().size() != 0) {
            receivers.setTotal(new BigInteger(String.valueOf(receivers.getReceivers().size())));
        }
        return receivers;
    }

    private Messages messagesToRest(List<Message> source) {
        Messages messages = new Messages();
        for (Message sourceMessage : source) {
            org.siemac.metamac.rest.notifications.v1_0.domain.Message targetMessage = new org.siemac.metamac.rest.notifications.v1_0.domain.Message();
            targetMessage.setText(sourceMessage.getText());
            targetMessage.setResources(externalItemsDoToResourcesRest(sourceMessage.getResources()));
            messages.getMessages().add(targetMessage);

        }

        if (!messages.getMessages().isEmpty()) {
            messages.setTotal(new BigInteger(String.valueOf(messages.getMessages().size())));
        }
        return messages;
    }

    private Resources externalItemsDoToResourcesRest(List<ExternalItem> resources) {
        // TODO: PEndiente de la transformación de los recursos

        // TODO Auto-generated method stub
        return null;
    }
}
