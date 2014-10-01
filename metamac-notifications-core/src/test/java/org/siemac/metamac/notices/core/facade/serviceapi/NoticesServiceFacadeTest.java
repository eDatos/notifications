package org.siemac.metamac.notices.core.facade.serviceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class NoticesServiceFacadeTest extends NoticesBaseTest implements NoticesServiceFacadeTestBase {

    @Autowired
    protected NoticesServiceFacade noticesServiceFacade;

    @Override
    public void testRetrieveNoticeByUrn() throws Exception {
        // TODO METAMAC-1984
    }

    @Override
    @Test
    public void testFindNotices() throws Exception {
        // TODO METAMAC-1984
    }

    @Override
    @Test
    public void testMarkNoticeAsRead() throws Exception {
        // TODO METAMAC-1984
    }

    @Override
    @Test
    public void testMarkNoticeAsUnread() throws Exception {
        // TODO METAMAC-1984
    }

    @Override
    @Test
    public void testSendAnnouncement() throws Exception {
        // TODO METAMAC-1984
    }
}
