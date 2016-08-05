package org.siemac.metamac.notices.web.client.enums;

import java.io.Serializable;

public enum ReceiverType implements Serializable {
    USERS, CONDITIONS;

    private ReceiverType() {
    }

    public String getName() {
        return name();
    }
}
