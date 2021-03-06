package org.siemac.metamac.rest.notices.v1_0;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.rest.notices.v1_0.service.NoticesRest2DoMapperTest;
import org.siemac.metamac.rest.notices.v1_0.service.NoticesRestInternalFacadeV10NotificationsTest;

// @formatter:off
@RunWith(Suite.class)
@Suite.SuiteClasses({NoticesRestInternalFacadeV10NotificationsTest.class,
                        NoticesRest2DoMapperTest.class})
// @formatter:on
public class NoticesRestInternalSuite {
}
