package org.siemac.metamac.rest.notices.v1_0.service;

import org.junit.Test;
import org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice.NoticesRest2DoMapperV10;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.utils.asserts.NoticesAsserts;
import org.siemac.metamac.rest.notices.v1_0.utils.factories.NoticesRestMockFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class NoticesRest2DoMapperTest extends NoticesRestInternalFacadeV10BaseTest {

    @Autowired
    NoticesRest2DoMapperV10 noticesRest2Do;

    @Test
    public void testNoticeRest2Do_Basic() throws Exception {
        Notice restNotice = NoticesRestMockFactory.getNotification01Basic();
        org.siemac.metamac.notices.core.notice.domain.Notice doNotice = noticesRest2Do.noticeRestToEntity(getServiceContextWithoutPrincipal(), restNotice);
        NoticesAsserts.assertEqualsNotice(doNotice, restNotice);
    }
}