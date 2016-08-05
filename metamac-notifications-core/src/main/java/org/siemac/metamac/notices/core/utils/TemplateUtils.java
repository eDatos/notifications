package org.siemac.metamac.notices.core.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TemplateUtils {

    private static final DateTimeFormatter VELOCITY_DATE_PATTER = DateTimeFormat.forPattern("HH:mm:ss - dd/MM/yyyy");

    public static String formatVelocityDate(DateTime dateTime) {
        return dateTime.toString(VELOCITY_DATE_PATTER);
    }

}
