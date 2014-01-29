package org.siemac.metamac.notices.web.server.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.siemac.metamac.notices.core.constants.NoticesConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.ApplicationStartupListener;

public class NoticesApplicationStartupListener extends ApplicationStartupListener {

    private static final Log LOG = LogFactory.getLog(NoticesApplicationStartupListener.class);

    @Override
    public void checkConfiguration() {

        LOG.info("****************************************************************");
        LOG.info("[metamac-notifications-web] Checking application configuration");
        LOG.info("****************************************************************");

        // SECURITY

        checkSecurityProperties();

        // DATASOURCE

        checkRequiredProperty(NoticesConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(NoticesConfigurationConstants.DB_URL);
        checkRequiredProperty(NoticesConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(NoticesConfigurationConstants.DB_PASSWORD);

        // OTHER CONFIGURATION PROPERTIES

        // Common properties

        checkEditionLanguagesProperty();
        checkNavBarUrlProperty();
        checkOrganisationProperty();

        // WEB APPLICATIONS
        checkRequiredProperty(NoticesConfigurationConstants.WEB_APPLICATION_STATISTICAL_OPERATIONS_INTERNAL_WEB);

        // API
        checkRequiredProperty(NoticesConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        checkRequiredProperty(NoticesConfigurationConstants.ENDPOINT_ACCESS_CONTROL_INTERNAL_API);

        // Notifications properties

        checkRequiredProperty(NoticesConfigurationConstants.USER_GUIDE_FILE_NAME);

        LOG.info("****************************************************************");
        LOG.info("[metamac-notifications-web] Application configuration checked");
        LOG.info("****************************************************************");
    }
}
