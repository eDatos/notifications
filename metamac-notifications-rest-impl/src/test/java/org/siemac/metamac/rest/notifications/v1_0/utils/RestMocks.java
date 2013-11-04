package org.siemac.metamac.rest.notifications.v1_0.utils;

import java.math.BigInteger;
import java.util.Date;

import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;
import org.siemac.metamac.rest.notifications.v1_0.domain.NotificationType;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receiver;
import org.siemac.metamac.rest.notifications.v1_0.domain.Receivers;

public class RestMocks {

    public static InternationalString mockInternationalString(String resourceID) {
        InternationalString internationalString = new InternationalString();
        internationalString.getTexts().add(mockLocalisedString("es", resourceID + " en Español"));
        internationalString.getTexts().add(mockLocalisedString("en", resourceID + " in English"));
        return internationalString;
    }

    private static LocalisedString mockLocalisedString(String lang, String value) {
        LocalisedString localisedString = new LocalisedString();
        localisedString.setLang(lang);
        localisedString.setValue(value);
        return localisedString;
    }

    public static Notification mockNotification_TYPE_NOTIFICATION() {
        Notification notification = new Notification();

        notification.setSendingApplication("application");
        notification.setSendingUser("user");
        notification.setEmail("user@domain.com");
        notification.setExpirationDate(new Date());
        notification.setRequiredRole("ADMIN");
        notification.setMessage("My message");
        notification.setNotificationType(NotificationType.NOTIFICATION);

        // Receivers
        Receivers receivers = new Receivers();
        receivers.setTotal(new BigInteger("5"));
        for (int i = 0; i < 5; i++) {
            Receiver receiver = new Receiver();
            receiver.setUsername("user-" + i);
            receivers.getReceivers().add(receiver);
        }
        notification.setReceivers(receivers);

        return notification;
    }

    public static Notification mockNotification_TYPE_NOTIFICATION(String urn) {
        Notification notification = mockNotification_TYPE_NOTIFICATION();
        notification.setUrn(urn);

        return notification;
    }
}