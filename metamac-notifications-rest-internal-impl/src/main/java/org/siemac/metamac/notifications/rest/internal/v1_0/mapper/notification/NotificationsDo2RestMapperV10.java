package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.notification;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.notice.domain.Notification;

public interface NotificationsDo2RestMapperV10 {

    public org.siemac.metamac.rest.notifications.v1_0.domain.Notification notificationEntity2Rest(ServiceContext ctx, Notification source) throws MetamacException;

}
