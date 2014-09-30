package org.siemac.metamac.notices.core.facade.serviceapi;

import static org.junit.Assert.fail;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class NoticesServiceFacadeTest extends AbstractDbUnitJpaTests implements NoticesServiceFacadeTestBase {

    @Autowired
    protected NoticesServiceFacade noticesServiceFacade;

    @Override
    public void testRetrieveNoticeByUrn() throws Exception {
        // TODO METAMAC-1984
        fail("testRetrieveNoticeByUrn not implemented");
    }

    @Override
    @Test
    public void testFindNotices() throws Exception {
        // TODO METAMAC-1984
        fail("testFindNotices not implemented");
    }

    @Override
    @Test
    public void testMarkNoticeAsRead() throws Exception {
        // TODO METAMAC-1984
        fail("testMarkNoticeAsRead not implemented");
    }

    @Override
    @Test
    public void testMarkNoticeAsUnread() throws Exception {
        // TODO METAMAC-1984
        fail("testMarkNoticeAsUnread not implemented");
    }

    @Override
    public void testSendAnnouncement() throws Exception {
        // TODO METAMAC-1984
        fail("testSendAnnouncement not implemented");
    }
}
