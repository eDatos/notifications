package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notices.core.notice.enume.domain.NotificationType;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base.BaseRest2DoMapperV10Impl;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base.CommonRest2DoMapperV10;
import org.siemac.metamac.rest.notifications.v1_0.domain.Application;
import org.siemac.metamac.rest.notifications.v1_0.domain.Applications;
import org.siemac.metamac.rest.notifications.v1_0.domain.Message;
import org.siemac.metamac.rest.notifications.v1_0.domain.Messages;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;
import org.siemac.metamac.rest.notifications.v1_0.domain.Role;
import org.siemac.metamac.rest.notifications.v1_0.domain.Roles;
import org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperation;
import org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsRest2DoMapperV10Impl extends BaseRest2DoMapperV10Impl implements NotificationsRest2DoMapperV10 {

    @Autowired
    private CommonRest2DoMapperV10 commonRest2DoMapper;

    @Override
    public org.siemac.metamac.notices.core.notice.domain.Notification notificationRestToEntity(ServiceContext ctx, Notification source) throws MetamacException {
        if (source == null) {
            return null;
        }

        org.siemac.metamac.notices.core.notice.domain.Notification target = new org.siemac.metamac.notices.core.notice.domain.Notification(source.getSendingApplication(), source.getSubject(),
                NotificationType.valueOf(source.getNotificationType().name()));
        target.setSendingUser(source.getSendingUser());
        target.setExpirationDate(CoreCommonUtil.transformDateToDateTime(source.getExpirationDate()));

        target.getMessages().addAll(messagesRestToEntity(source.getMessages()));
        target.getReceivers().addAll(receiversRestToEntity(source.getReceivers()));
        target.getApps().addAll(applicationsRestToEntity(source.getApplications()));
        target.getStatisticalOperations().addAll(statisticalOperationsRestToEntity(source.getStatisticalOperations()));
        target.getRoles().addAll(rolesRestToEntity(source.getRoles()));

        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.Role> rolesRestToEntity(Roles source) {
        List<org.siemac.metamac.notices.core.notice.domain.Role> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.Role>();
        if (source != null) {
            Set<String> rolCodes = new HashSet<String>();
            for (Role role : source.getRoles()) {
                if (!rolCodes.contains(role.getName())) {
                    rolCodes.add(role.getName());
                    org.siemac.metamac.notices.core.notice.domain.Role roleElement = new org.siemac.metamac.notices.core.notice.domain.Role();
                    roleElement.setName(role.getName());
                    target.add(roleElement);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.StatisticalOperation> statisticalOperationsRestToEntity(StatisticalOperations source) {
        List<org.siemac.metamac.notices.core.notice.domain.StatisticalOperation> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.StatisticalOperation>();
        if (source != null) {
            Set<String> rolCodes = new HashSet<String>();
            for (StatisticalOperation statisticalOperation : source.getStatisticalOperations()) {
                if (!rolCodes.contains(statisticalOperation.getUrn())) {
                    rolCodes.add(statisticalOperation.getUrn());
                    org.siemac.metamac.notices.core.notice.domain.StatisticalOperation statisticalOperationElement = new org.siemac.metamac.notices.core.notice.domain.StatisticalOperation();
                    statisticalOperationElement.setName(statisticalOperation.getUrn());
                    target.add(statisticalOperationElement);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.App> applicationsRestToEntity(Applications source) {
        List<org.siemac.metamac.notices.core.notice.domain.App> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.App>();
        if (source != null) {
            Set<String> applicationCodes = new HashSet<String>();
            for (Application application : source.getApplications()) {
                if (!applicationCodes.contains(application.getName())) {
                    applicationCodes.add(application.getName());
                    org.siemac.metamac.notices.core.notice.domain.App app = new org.siemac.metamac.notices.core.notice.domain.App();
                    app.setName(application.getName());
                    target.add(app);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.Receiver> receiversRestToEntity(Receivers source) {
        List<org.siemac.metamac.notices.core.notice.domain.Receiver> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.Receiver>();

        if (source != null) {
            Set<String> usernamesInNotifications = new HashSet<String>();
            for (org.siemac.metamac.rest.notifications.v1_0.domain.Receiver sourceReceiver : source.getReceivers()) {
                if (!usernamesInNotifications.contains(sourceReceiver.getUsername())) {
                    usernamesInNotifications.add(sourceReceiver.getUsername());
                    org.siemac.metamac.notices.core.notice.domain.Receiver receiverElement = new org.siemac.metamac.notices.core.notice.domain.Receiver();
                    receiverElement.setUsername(sourceReceiver.getUsername());
                    target.add(receiverElement);
                }
            }
        }
        return target;
    }

    private List<org.siemac.metamac.notices.core.notice.domain.Message> messagesRestToEntity(Messages source) {
        List<org.siemac.metamac.notices.core.notice.domain.Message> target = new ArrayList<org.siemac.metamac.notices.core.notice.domain.Message>();

        // Messages
        if (source != null) {
            for (Message sourceMessage : source.getMessages()) {
                org.siemac.metamac.notices.core.notice.domain.Message messageElement = new org.siemac.metamac.notices.core.notice.domain.Message(sourceMessage.getText());

                // TODO: Añadir resources
                target.add(messageElement);
            }
        }

        return target;
    }
}
