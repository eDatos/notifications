package org.siemac.metamac.notices.core.facade.serviceapi;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class NoticesCoreServiceFacadeTest extends AbstractDbUnitJpaTests implements NoticesCoreServiceFacadeTestBase {

    @Autowired
    protected NoticesCoreServiceFacade noticesCoreServiceFacade;

    @Override
    public void testFindNotices() throws Exception {
        // TODO METAMAC-1984

    }
}
