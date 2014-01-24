package org.siemac.metamac.notifications.core.utils.mocks.templates;

import org.siemac.metamac.common.test.utils.MetamacMocks;

public class NotificationsPersistedDoMocks extends MetamacMocks {

    private static NotificationsPersistedDoMocks instance;

    private NotificationsPersistedDoMocks() {
    }

    public static NotificationsPersistedDoMocks getInstance() {
        if (instance == null) {
            instance = new NotificationsPersistedDoMocks();
        }
        return instance;
    }
}