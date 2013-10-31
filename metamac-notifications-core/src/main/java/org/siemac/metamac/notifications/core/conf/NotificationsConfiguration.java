package org.siemac.metamac.notifications.core.conf;

import java.util.Map;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface NotificationsConfiguration extends ConfigurationService {

    static enum KeyDotEnum {
        ONE_DOT, TWO_DOT, THREE_DOT, FOUR_DOT, FIVE_DOT, SIX_DOT;
    }

    public Map<KeyDotEnum, String> retrieveDotCodeMapping() throws MetamacException;

}
