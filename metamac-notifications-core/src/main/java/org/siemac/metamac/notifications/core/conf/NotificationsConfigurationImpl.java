package org.siemac.metamac.notifications.core.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.notifications.core.constants.NotificationsConfigurationConstants;
import org.siemac.metamac.notifications.core.error.ServiceExceptionType;

public class NotificationsConfigurationImpl extends ConfigurationServiceImpl implements NotificationsConfiguration {

    private Map<KeyDotEnum, String> dotCodeMappingMap = null;

    @Override
    public Map<KeyDotEnum, String> retrieveDotCodeMapping() throws MetamacException {

        if (dotCodeMappingMap == null) {
            Map<KeyDotEnum, String> dotCodeMappingMap = new HashMap<NotificationsConfiguration.KeyDotEnum, String>(6);
            List<Object> dotCodeMappingList = retrievePropertyList(NotificationsConfigurationConstants.DOT_CODE_MAPPING, true);

            for (Object item : dotCodeMappingList) {
                String[] splitItem = ((String) item).split("=");
                try {
                    KeyDotEnum key = KeyDotEnum.valueOf(splitItem[0].trim());
                    dotCodeMappingMap.put(key, splitItem[1].trim());
                } catch (IllegalArgumentException e) {
                    throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_INVALID)
                            .withMessageParameters(NotificationsConfigurationConstants.DOT_CODE_MAPPING).build();
                }
            }

            if (dotCodeMappingMap.size() != 6) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_INVALID)
                        .withMessageParameters(NotificationsConfigurationConstants.DOT_CODE_MAPPING).build();
            }

            this.dotCodeMappingMap = dotCodeMappingMap;
        }

        return dotCodeMappingMap;
    }
}
