package org.siemac.metamac.notices.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.constants.NoticesConfigurationConstants;

public class NoticesConfigurationImpl extends ConfigurationServiceImpl implements NoticesConfiguration {

    @Override
    public String retrieveChannelMailHost() throws MetamacException {
        return retrieveProperty(NoticesConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_HOST);
    }

    @Override
    public String retrieveChannelMailPort() throws MetamacException {
        return retrieveProperty(NoticesConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_PORT);
    }

    @Override
    public String retrieveChannelMailUsername() throws MetamacException {
        return retrieveProperty(NoticesConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_USERNAME);
    }

    @Override
    public String retrieveChannelMailPassword() throws MetamacException {
        return retrieveProperty(NoticesConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_PASSWORD);
    }

    @Override
    public String retrieveChannelMailProtocol() throws MetamacException {
        return retrieveProperty(NoticesConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_PROTOCOL);
    }
}
