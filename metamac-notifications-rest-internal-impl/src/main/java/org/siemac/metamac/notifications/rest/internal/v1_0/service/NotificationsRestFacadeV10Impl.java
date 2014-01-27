package org.siemac.metamac.notifications.rest.internal.v1_0.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.siemac.metamac.notifications.core.notice.serviceapi.NotificationService;
import org.siemac.metamac.notifications.rest.internal.NotificationsRestConstants;
import org.siemac.metamac.notifications.rest.internal.exception.NotificationsRestServiceExceptionType;
import org.siemac.metamac.notifications.rest.internal.service.utils.NotificationsRestInternalUtils;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.notification.NotificationsDo2RestMapperV10;
import org.siemac.metamac.notifications.rest.internal.v1_0.mapper.notification.NotificationsRest2DoMapperV10;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("notificationsRestInternalFacadeV10")
public class NotificationsRestFacadeV10Impl implements NotificationsV1_0 {

    @Autowired
    private NotificationService           notificationService;

    @Autowired
    private NotificationsRest2DoMapperV10 notificationsRest2DoMapperV10;

    @Autowired
    private NotificationsDo2RestMapperV10 notificationsDo2RestMapperV10;

    @Override
    public Response createNotification(Notification notification) {
        try {
            // Transform
            org.siemac.metamac.notifications.core.notice.domain.Notification notificationEntity = notificationsRest2DoMapperV10.notificationRestToEntity(NotificationsRestConstants.SERVICE_CONTEXT,
                    notification);

            // Create
            notificationService.createNotification(NotificationsRestConstants.SERVICE_CONTEXT, notificationEntity);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw NotificationsRestInternalUtils.manageException(e);
        }
    }

    @Override
    public Notification retrieveResourceByUrn(String urn) {
        try {
            // Retrieve
            org.siemac.metamac.notifications.core.notice.domain.Notification notificationEntity = notificationService.retrieveNotificationByUrn(NotificationsRestConstants.SERVICE_CONTEXT, urn);

            if (notificationEntity == null) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(NotificationsRestServiceExceptionType.NOTIFICATION_NOT_FOUND, urn);
                throw new RestException(exception, Status.NOT_FOUND);
            }

            // Transform
            Notification notificationRest2Entity = notificationsDo2RestMapperV10.notificationEntity2Rest(NotificationsRestConstants.SERVICE_CONTEXT, notificationEntity);
            return notificationRest2Entity;
        } catch (Exception e) {
            throw NotificationsRestInternalUtils.manageException(e);
        }
    }
}
