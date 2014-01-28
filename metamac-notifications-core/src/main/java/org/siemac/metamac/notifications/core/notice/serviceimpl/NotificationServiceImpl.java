package org.siemac.metamac.notifications.core.notice.serviceimpl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.notifications.core.channel.mail.serviceimpl.MailChannelService;
import org.siemac.metamac.notifications.core.error.ServiceExceptionType;
import org.siemac.metamac.notifications.core.invocation.service.AccessControlRestInternalFacade;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.exception.NotificationNotFoundException;
import org.siemac.metamac.notifications.core.notice.serviceapi.validators.NotificationServiceInvocationValidator;
import org.siemac.metamac.notifications.core.notice.serviceimpl.util.NotificationServiceUtil;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of NotificationService.
 */
@Service("notificationService")
public class NotificationServiceImpl extends NotificationServiceImplBase {

    @Autowired
    private NotificationServiceInvocationValidator notificationServiceInvocationValidator;

    @Autowired
    private MailChannelService                     mailChannelService;

    @Autowired
    private AccessControlRestInternalFacade        accessControlRestInternalFacade;

    public NotificationServiceImpl() {
    }

    @Override
    public Notification findNotificationById(ServiceContext ctx, Long id) throws MetamacException {

        // Validations
        notificationServiceInvocationValidator.checkFindNotificationById(ctx, id);

        try {
            return getNotificationRepository().findById(id);
        } catch (NotificationNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.NOTIFICATION_NOT_FOUND, id);
        }
    }

    @Override
    public Notification retrieveNotificationByUrn(ServiceContext ctx, String urn) throws MetamacException {

        // Validations
        notificationServiceInvocationValidator.checkRetrieveNotificationByUrn(ctx, urn);

        return getNotificationRepository().retrieveByUrn(urn);
    }

    @Override
    public Notification createNotification(ServiceContext ctx, Notification notification) throws MetamacException {

        // Validations
        notificationServiceInvocationValidator.checkCreateNotification(ctx, notification);

        // Generate URN
        // FIXME: change el random por un get del uuid
        notification.setUrn(GeneratorUrnUtils.generateSiemacNoticeUrn(java.util.UUID.randomUUID().toString()));

        notification = getNotificationRepository().save(notification);

        // Send notification
        mailChannelService.sendMail(ctx, notification, extractMailsTo(notification), extractReplyTo(notification));

        return notification;
    }

    @Override
    public Notification updateNotification(ServiceContext ctx, Notification notification) throws MetamacException {

        // Validations
        notificationServiceInvocationValidator.checkUpdateNotification(ctx, notification);

        return getNotificationRepository().save(notification);
    }

    @Override
    public void deleteNotification(ServiceContext ctx, Long id) throws MetamacException {

        // Validations
        notificationServiceInvocationValidator.checkDeleteNotification(ctx, id);

        Notification notification = findNotificationById(ctx, id);
        getNotificationRepository().delete(notification);
    }

    @Override
    public List<Notification> findAllNotification(ServiceContext ctx) throws MetamacException {

        // Validations
        notificationServiceInvocationValidator.checkFindAllNotification(ctx);

        return getNotificationRepository().findAll();
    }

    @Override
    public List<Notification> findNotificationByCondition(ServiceContext ctx, List<ConditionalCriteria> condition) throws MetamacException {

        // Validations
        notificationServiceInvocationValidator.checkFindNotificationByCondition(ctx, condition);

        initCriteriaConditions(condition, Notification.class);

        return getNotificationRepository().findByCondition(condition);
    }

    // ----------------------------------------------------------------------
    // UTILS
    // ----------------------------------------------------------------------
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<ConditionalCriteria> initCriteriaConditions(List<ConditionalCriteria> conditions, Class entityClass) {
        List<ConditionalCriteria> conditionsEntity = ConditionalCriteriaBuilder.criteriaFor(entityClass).build();
        if (conditions != null) {
            conditionsEntity.addAll(conditions);
        }
        return conditionsEntity;
    }

    private String[] extractMailsTo(Notification notification) throws MetamacException {
        String queryForFindUsers = NotificationServiceUtil.createQueryForFindUsers(notification);

        if (StringUtils.isEmpty(queryForFindUsers)) {
            throw new RuntimeException("Unexpected error: Impossible to create query for acces-control api");
        }

        List<User> users = accessControlRestInternalFacade.findUsers(queryForFindUsers);

        if (users.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.NOTIFICATION_RECEIVERS_NOT_FOUND);
        }

        String[] mailsTo = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            mailsTo[i] = users.get(i).getMail();
        }
        return mailsTo;
    }

    private String extractReplyTo(Notification notification) throws MetamacException {
        String queryForFindUsers = NotificationServiceUtil.createQueryForFindUser(notification.getSendingUser());

        if (StringUtils.isEmpty(queryForFindUsers)) {
            throw new RuntimeException("Unexpected error: Impossible to create query for access-control api");
        }

        List<User> users = accessControlRestInternalFacade.findUsers(queryForFindUsers);

        if (users.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.NOTIFICATION_RECEIVERS_NOT_FOUND);
        }

        return users.iterator().next().getMail();
    }
}
