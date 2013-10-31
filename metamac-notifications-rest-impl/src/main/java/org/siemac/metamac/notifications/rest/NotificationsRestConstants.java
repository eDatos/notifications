package org.siemac.metamac.notifications.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.api.constants.RestApiConstants;

public class NotificationsRestConstants extends RestApiConstants {

    public static final ServiceContext SERVICE_CONTEXT = new ServiceContext("restNotifications", "restNotifications", "restNotifications");

    public static String               API_NAME        = "notifications";
    public static String               API_VERSION_1_0 = "v1.0";

}
