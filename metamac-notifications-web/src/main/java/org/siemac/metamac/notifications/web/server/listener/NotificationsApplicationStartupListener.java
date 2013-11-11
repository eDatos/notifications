package org.siemac.metamac.notifications.web.server.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.siemac.metamac.notifications.core.constants.NotificationsConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.ApplicationStartupListener;

public class NotificationsApplicationStartupListener extends ApplicationStartupListener {

    private static final Log LOG = LogFactory.getLog(NotificationsApplicationStartupListener.class);

    @Override
    public void checkConfiguration() {

        LOG.info("****************************************************************");
        LOG.info("[metamac-notifications-web] Checking application configuration");
        LOG.info("****************************************************************");

        // SECURITY

        checkSecurityProperties();

        // DATASOURCE

        checkRequiredProperty(NotificationsConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(NotificationsConfigurationConstants.DB_URL);
        checkRequiredProperty(NotificationsConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(NotificationsConfigurationConstants.DB_PASSWORD);

        // OTHER CONFIGURATION PROPERTIES

        // Common properties

        checkEditionLanguagesProperty();
        checkNavBarUrlProperty();
        checkOrganisationProperty();

        // WEB APPLICATIONS
        checkRequiredProperty(NotificationsConfigurationConstants.WEB_APPLICATION_STATISTICAL_OPERATIONS_INTERNAL_WEB);

        // API
        checkRequiredProperty(NotificationsConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        checkRequiredProperty(NotificationsConfigurationConstants.ENDPOINT_ACCESS_CONTROL_INTERNAL_API);

        // Notifications properties

        checkRequiredProperty(NotificationsConfigurationConstants.USER_GUIDE_FILE_NAME);

        LOG.info("****************************************************************");
        LOG.info("[metamac-notifications-web] Application configuration checked");
        LOG.info("****************************************************************");
    }
}
