package org.siemac.metamac.notices.core.utils.mocks.templates;

import org.siemac.metamac.common.test.utils.MetamacMocks;

public class NoticesPersistedDoMocks extends MetamacMocks {

    private static NoticesPersistedDoMocks instance;

    private NoticesPersistedDoMocks() {
    }

    public static NoticesPersistedDoMocks getInstance() {
        if (instance == null) {
            instance = new NoticesPersistedDoMocks();
        }
        return instance;
    }
}