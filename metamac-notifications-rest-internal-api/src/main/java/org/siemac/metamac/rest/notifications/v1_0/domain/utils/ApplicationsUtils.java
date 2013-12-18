package org.siemac.metamac.rest.notifications.v1_0.domain.utils;

import org.siemac.metamac.rest.notifications.v1_0.domain.Applications;

public class ApplicationsUtils {

    public static Applications createApplicationsList(String... applicationsCodes) {
        Applications applications = new Applications();
        for (String appName : applicationsCodes) {
            applications.getApplications().add(ApplicationBuilder.application().withName(appName).build());
        }
        return applications;
    }
}
