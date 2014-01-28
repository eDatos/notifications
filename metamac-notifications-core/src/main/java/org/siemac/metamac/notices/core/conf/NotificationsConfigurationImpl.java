package org.siemac.metamac.notices.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.constants.NotificationsConfigurationConstants;

public class NotificationsConfigurationImpl extends ConfigurationServiceImpl implements NotificationsConfiguration {

    private String channelMailHost;
    private String channelMailPort;
    private String channelMailUsername;
    private String channelMailPassword;
    private String channelMailProtocol;

    @Override
    public String retrieveChannelMailHost() throws MetamacException {
        if (channelMailHost == null) {
            channelMailHost = retrieveProperty(NotificationsConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_HOST, Boolean.TRUE);
        }
        return channelMailHost;
    }

    @Override
    public String retrieveChannelMailPort() throws MetamacException {
        if (channelMailPort == null) {
            channelMailPort = retrieveProperty(NotificationsConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_PORT, Boolean.TRUE);
        }
        return channelMailPort;
    }

    @Override
    public String retrieveChannelMailUsername() throws MetamacException {
        if (channelMailUsername == null) {
            channelMailUsername = retrieveProperty(NotificationsConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_USERNAME, Boolean.TRUE);
        }
        return channelMailUsername;
    }

    @Override
    public String retrieveChannelMailPassword() throws MetamacException {
        if (channelMailPassword == null) {
            channelMailPassword = retrieveProperty(NotificationsConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_PASSWORD, Boolean.TRUE);
        }
        return channelMailPassword;
    }

    @Override
    public String retrieveChannelMailProtocol() throws MetamacException {
        if (channelMailProtocol == null) {
            channelMailProtocol = retrieveProperty(NotificationsConfigurationConstants.METAMAC_NOTIFICATION_CHANNEL_MAIL_PROTOCOL, Boolean.TRUE);
        }
        return channelMailProtocol;
    }
}
