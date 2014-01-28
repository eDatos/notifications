package org.siemac.metamac.notifications.rest.internal;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.api.constants.RestApiConstants;

public class NotificationsRestConstants extends RestApiConstants {

    public static final ServiceContext SERVICE_CONTEXT           = new ServiceContext("restNotifications", "restNotifications", "restNotifications");

    public static String               API_NAME                  = "notifications";
    public static String               API_VERSION_1_0           = "v1.0";

    public static String               KIND_NOTIFICATION         = API_NAME + KIND_SEPARATOR + "notification";

    public static final String         LINK_SUBPATH_NOTIFICATION = "notifications";
}
