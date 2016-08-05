package org.siemac.metamac.notices.core.conf;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface NoticesConfigurationService extends ConfigurationService {

    public String retrieveHelpUrl() throws MetamacException;
    public String retrieveDocsPath() throws MetamacException;

    public String retrieveChannelMailHost() throws MetamacException;
    public String retrieveChannelMailPort() throws MetamacException;
    public String retrieveChannelMailUsername() throws MetamacException;
    public String retrieveChannelMailProtocol() throws MetamacException;
    public String retrieveChannelMailPassword() throws MetamacException;
}
