package org.siemac.metamac.notifications.rest.v1_0.mapper.notification;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;

public interface NotificationsRest2DoMapperV10 {

    public org.siemac.metamac.notifications.core.notice.domain.Notification notificationRest2Entity(ServiceContext ctx, Notification notification) throws MetamacException;

}
