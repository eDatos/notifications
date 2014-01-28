package org.siemac.metamac.notifications.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface NotificationsConfiguration extends ConfigurationService {

    public String retrieveChannelMailHost() throws MetamacException;

    public String retrieveChannelMailPort() throws MetamacException;

    public String retrieveChannelMailUsername() throws MetamacException;

    public String retrieveChannelMailProtocol() throws MetamacException;

    public String retrieveChannelMailPassword() throws MetamacException;

}