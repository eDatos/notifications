package org.siemac.metamac.rest.notices.v1_0.service;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice.NoticesRest2DoMapperV10;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.utils.asserts.NoticesAsserts;
import org.siemac.metamac.rest.notices.v1_0.utils.factories.NoticesRestMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices-rest-internal/applicationContext-test.xml"})
public class NoticesRest2DoMapperTest {

    @Autowired
    NoticesRest2DoMapperV10 noticesRest2Do;

    @Test
    public void testNoticeRest2Do_Basic() throws Exception {
        Notice restNotice = NoticesRestMockFactory.getNotification01Basic();
        org.siemac.metamac.notices.core.notice.domain.Notice doNotice = noticesRest2Do.noticeRestToEntity(getServiceContextWithoutPrincipal(), restNotice);
        NoticesAsserts.assertEqualsNotice(doNotice, restNotice);
    }

    protected ServiceContext getServiceContextWithoutPrincipal() {
        return new ServiceContext("junit", "junit", "app");
    }

}