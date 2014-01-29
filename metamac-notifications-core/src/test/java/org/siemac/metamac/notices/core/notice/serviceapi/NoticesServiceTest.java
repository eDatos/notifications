package org.siemac.metamac.notices.core.notice.serviceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class NoticesServiceTest extends NoticesBaseTest implements NoticesServiceTestBase {

    @Autowired
    protected NoticesService noticeService;

    @Override
    @Test
    public void testFindNoticeById() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testCreateNotice() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testUpdateNotice() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testDeleteNotice() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testFindAllNotice() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testFindNoticeByCondition() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testRetrieveNoticeByUrn() throws Exception {
        // TODO Auto-generated method stub

    }
}
