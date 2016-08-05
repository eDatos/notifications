package org.siemac.metamac.notices.web.server.listener;

import org.siemac.metamac.notices.core.constants.NoticesConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.InternalApplicationStartupListener;

public class NoticesApplicationStartupListener extends InternalApplicationStartupListener {

    @Override
    public void checkDatasourceProperties() {
        checkRequiredProperty(NoticesConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(NoticesConfigurationConstants.DB_URL);
        checkRequiredProperty(NoticesConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(NoticesConfigurationConstants.DB_PASSWORD);
    }

    @Override
    public void checkWebApplicationsProperties() {
        checkRequiredProperty(NoticesConfigurationConstants.WEB_APPLICATION_STATISTICAL_OPERATIONS_INTERNAL_WEB);
    }

    @Override
    public void checkApiProperties() {
        checkRequiredProperty(NoticesConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        checkRequiredProperty(NoticesConfigurationConstants.ENDPOINT_ACCESS_CONTROL_INTERNAL_API);
    }

    @Override
    public void checkOtherModuleProperties() {
        checkRequiredProperty(NoticesConfigurationConstants.HELP_URL);
    }

    @Override
    public String projectName() {
        return "notices-internal";
    }
}
