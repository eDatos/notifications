package org.siemac.metamac.rest.notifications.v1_0.utils;

import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;

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
}