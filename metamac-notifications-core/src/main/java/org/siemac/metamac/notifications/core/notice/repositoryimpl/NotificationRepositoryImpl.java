package org.siemac.metamac.notifications.core.notice.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notifications.core.error.ServiceExceptionType;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.domain.NotificationProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Notification
 */
@Repository("notificationRepository")
public class NotificationRepositoryImpl extends NotificationRepositoryBase {

    public NotificationRepositoryImpl() {
    }

    @Override
    public Notification retrieveByUrn(String urn) throws MetamacException {

        List<ConditionalCriteria> condition = criteriaFor(Notification.class).withProperty(NotificationProperties.urn()).eq(urn).distinctRoot().build();

        List<Notification> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.NOTIFICATION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one notification with urn " + urn);
        }

        return result.get(0);
    }
}
