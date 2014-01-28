package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import java.math.BigInteger;

import org.siemac.metamac.rest.notices.v1_0.domain.Applications;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;

public class ApplicationsUtils {

    public static Applications createApplicationsList(MetamacApplicationsEnum... applicationsCodes) {
        Applications applications = new Applications();
        for (MetamacApplicationsEnum appName : applicationsCodes) {
            applications.getApplications().add(ApplicationBuilder.application().withName(appName.toString()).build());
        }

        applications.setTotal(new BigInteger(String.valueOf(applications.getApplications().size())));
        return applications;
    }
}
