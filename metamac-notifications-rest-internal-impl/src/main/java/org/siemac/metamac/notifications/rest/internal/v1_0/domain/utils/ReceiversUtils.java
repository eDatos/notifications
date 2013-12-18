package org.siemac.metamac.notifications.rest.internal.v1_0.domain.utils;

import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;

public class ReceiversUtils {

    public static Receivers createReceiversList(String... receiversCodes) {
        Receivers receivers = new Receivers();
        for (String username : receiversCodes) {
            receivers.getReceivers().add(ReceiverBuilder.receiver().withUsername(username).build());
        }
        return receivers;
    }
}
