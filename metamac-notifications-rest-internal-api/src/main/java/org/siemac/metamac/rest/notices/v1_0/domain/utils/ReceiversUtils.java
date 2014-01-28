package org.siemac.metamac.rest.notifications.v1_0.domain.utils;

import java.math.BigInteger;

import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;

public class ReceiversUtils {

    public static Receivers createReceiversList(String... receiversCodes) {
        Receivers receivers = new Receivers();
        for (String username : receiversCodes) {
            receivers.getReceivers().add(ReceiverBuilder.receiver().withUsername(username).build());
        }

        receivers.setTotal(new BigInteger(String.valueOf(receivers.getReceivers().size())));
        return receivers;
    }
}
