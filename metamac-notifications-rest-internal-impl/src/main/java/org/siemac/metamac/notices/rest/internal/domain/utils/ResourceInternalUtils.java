package org.siemac.metamac.notices.rest.internal.domain.utils;

import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;

public class ResourceInternalUtils {

    public static String getLocalisedLabel(InternationalString internationalString, String locale) {
        LocalisedString localstr = getLocalised(internationalString, locale);
        if (localstr != null) {
            return localstr.getValue();
        }
        return null;
    }

    public static LocalisedString getLocalised(InternationalString internationalString, String locale) {
        if (locale == null) {
            return null;
        }
        for (LocalisedString localstr : internationalString.getTexts()) {
            if (locale.equalsIgnoreCase(localstr.getLang())) {
                return localstr;
            }
        }
        return null;
    }
}
