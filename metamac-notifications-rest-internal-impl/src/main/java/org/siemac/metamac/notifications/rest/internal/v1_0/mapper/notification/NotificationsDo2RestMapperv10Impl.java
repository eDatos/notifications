package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.notification;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.notice.domain.App;
import org.siemac.metamac.notifications.core.notice.domain.Message;
import org.siemac.metamac.notifications.core.notice.domain.Receiver;
import org.siemac.metamac.notifications.core.notice.domain.Role;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.notifications.v1_0.domain.Application;
import org.siemac.metamac.rest.notifications.v1_0.domain.Applications;
import org.siemac.metamac.rest.notifications.v1_0.domain.Messages;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationType;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;
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

        target.setUrn(source.getUrn());
        target.setSendingApplication(source.getSendingApplication());
        target.setSendingUser(source.getSendingUser());
        target.setSendingDate(CoreCommonUtil.transformDateTimeToDate(source.getCreatedDate()));
        target.setExpirationDate(CoreCommonUtil.transformDateTimeToDate(source.getExpirationDate()));
        target.setNotificationType(NotificationType.fromValue(source.getNotificationType().getName()));

        // Messages
        {
            Messages messages = new Messages();
            for (Message sourceMessage : source.getMessages()) {
                org.siemac.metamac.rest.notifications.v1_0.domain.Message targetMessage = new org.siemac.metamac.rest.notifications.v1_0.domain.Message();
                targetMessage.setText(sourceMessage.getText());
                targetMessage.setResources(externalItemsDoToResourcesRest(sourceMessage.getResources()));
                messages.getMessages().add(targetMessage);

            }
            if (messages.getMessages().size() != 0) {
                messages.setTotal(new BigInteger(String.valueOf(messages.getMessages().size())));
                target.setMessages(messages);
            }
        }

        // Receivers
        {
            Receivers receivers = new Receivers();
            for (Receiver sourceReceiver : source.getReceivers()) {
                org.siemac.metamac.rest.notifications.v1_0.domain.Receiver targetReceiver = new org.siemac.metamac.rest.notifications.v1_0.domain.Receiver();
                targetReceiver.setUsername(sourceReceiver.getUsername());
                receivers.getReceivers().add(targetReceiver);
            }
            if (receivers.getReceivers().size() != 0) {
                receivers.setTotal(new BigInteger(String.valueOf(receivers.getReceivers().size())));
                target.setReceivers(receivers);
            }
        }

        // Applications
        {
            Applications applications = new Applications();
            for (App app : source.getApps()) {
                Application targetApplication = new Application();
                targetApplication.setName(app.getName());
                applications.getApplications().add(targetApplication);
            }
            if (applications.getApplications().size() != 0) {
                applications.setTotal(new BigInteger(String.valueOf(applications.getApplications().size())));
                target.setApplications(applications);
            }
        }

        // Roles
        {
            Roles roles = new Roles();
            for (Role role : source.getRoles()) {
                org.siemac.metamac.rest.notifications.v1_0.domain.Role targetRole = new org.siemac.metamac.rest.notifications.v1_0.domain.Role();
                targetRole.setName(role.getName());
                roles.getRoles().add(targetRole);
            }
            if (roles.getRoles().size() != 0) {
                roles.setTotal(new BigInteger(String.valueOf(roles.getRoles().size())));
                target.setRoles(roles);
            }
        }

        // Statistical Operations
        {
            Resources resources = new Resources();
            for (ExternalItem statOpeExternalItem : source.getStatisticalOperations()) {
                Resource statOpeResource = commonDo2RestMapper.toResourceExternalItemStatisticalOperations(statOpeExternalItem, new ArrayList<String>());
                resources.getResources().add(statOpeResource);
            }
            if (resources.getResources().size() != 0) {
                resources.setTotal(new BigInteger(String.valueOf(resources.getResources().size())));
                target.setStatisticalOperations(resources);
            }
        }

        return target;
    }

    private Resources externalItemsDoToResourcesRest(List<ExternalItem> resources) {
        // TODO: PEndiente de la transformación de los recursos

        // TODO Auto-generated method stub
        return null;
    }
}
