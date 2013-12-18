package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.notification;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.error.ServiceExceptionType;
import org.siemac.metamac.notifications.core.notice.domain.App;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.domain.NotificationProperties;
import org.siemac.metamac.notifications.core.notice.enume.domain.NotificationType;
import org.siemac.metamac.notifications.core.notice.serviceapi.NotificationService;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base.BaseRest2DoMapperV10Impl;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base.CommonRest2DoMapperV10;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.notifications.v1_0.domain.Application;
import org.siemac.metamac.rest.notifications.v1_0.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationsRest2DoMapperV10Impl extends BaseRest2DoMapperV10Impl implements NotificationsRest2DoMapperV10 {

    @Autowired
    private NotificationService    notificationService;

    @Autowired
    private CommonRest2DoMapperV10 commonRest2DoMapper;

    @Override
    public Notification notificationRest2Entity(ServiceContext ctx, org.siemac.metamac.rest.notifications.v1_0.domain.Notification source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Find
        Notification notification = null;
        List<Notification> notifications = notificationService.findNotificationByCondition(ctx, criteriaFor(Notification.class).withProperty(NotificationProperties.urn()).eq(source.getUrn())
                .distinctRoot().build());
        if (notifications.size() == 1) {
            throw new MetamacException(ServiceExceptionType.NOTIFICATION_NOT_FOUND, source.getUrn());
        } else if (notifications.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one notification with urn " + source.getUrn());
        }

        Notification target = null;

        // Update
        if (notification != null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.NOTIFICATION_ALREADY_EXISTS).withMessageParameters(source.getUrn()).build();
            // If updated is supported uncommented this code, and check the mapping code
            // target = notification;
        } else {
            // New Object
            target = new Notification(source.getSendingApplication(), source.getMessage(), NotificationType.valueOf(source.getNotificationType().name()));
        }

        // target.setStatisticalOperation(commonRest2DoMapper.externalItemRestStatisticalOperationToExternalItemDo(source.getStatisticalOperation(), target.getStatisticalOperation()));

        target.setSendingUser(source.getSendingUser());
        target.setMail(source.getEmail());
        target.setExpirationDate(CoreCommonUtil.transformDateToDateTime(source.getExpirationDate()));
        // target.setRole(source.getRole());
        target.setMessage(source.getMessage());

        // Receivers
        if (source.getReceivers() != null) {
            Set<String> usernamesInNotifications = new HashSet<String>();
            for (org.siemac.metamac.rest.notifications.v1_0.domain.Receiver sourceReceiver : source.getReceivers().getReceivers()) {
                if (!usernamesInNotifications.contains(sourceReceiver.getUsername())) {
                    usernamesInNotifications.add(sourceReceiver.getUsername());
                    org.siemac.metamac.notifications.core.notice.domain.Receiver receiverElement = new org.siemac.metamac.notifications.core.notice.domain.Receiver();
                    receiverElement.setUsername(sourceReceiver.getUsername());
                    target.addReceiver(receiverElement);
                }
            }
        }

        // Applications
        if (source.getApplications() != null) {
            Set<String> applicationCodes = new HashSet<String>();
            for (Application application : source.getApplications().getApplications()) {
                if (!applicationCodes.contains(application.getName())) {
                    applicationCodes.add(application.getName());
                    App app = new App();
                    app.setName(application.getName());
                    target.addApp(app);
                }
            }
        }

        // Roles
        if (source.getRoles() != null) {
            Set<String> rolCodes = new HashSet<String>();
            for (Role role : source.getRoles().getRoles()) {
                if (!rolCodes.contains(role.getName())) {
                    rolCodes.add(role.getName());
                    org.siemac.metamac.notifications.core.notice.domain.Role roleElement = new org.siemac.metamac.notifications.core.notice.domain.Role();
                    roleElement.setName(role.getName());
                    target.addRole(roleElement);
                }
            }
        }

        // Statistical operations
        if (source.getStatisticalOperations() != null) {
            Set<String> statisticalOperationsUrns = new HashSet<String>();
            for (Resource resource : source.getStatisticalOperations().getResources()) {
                if (!statisticalOperationsUrns.contains(resource.getUrn())) {
                    statisticalOperationsUrns.add(resource.getUrn());
                    ExternalItem externalItem = commonRest2DoMapper.externalItemRestStatisticalOperationToExternalItemDo(resource, null);
                    target.addStatisticalOperation(externalItem);
                }
            }
        }

        return target;
    }
}
