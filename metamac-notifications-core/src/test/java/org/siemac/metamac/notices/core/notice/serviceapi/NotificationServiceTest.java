package org.siemac.metamac.notices.core.notice.serviceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NotificationsBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notifications/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class NotificationServiceTest extends NotificationsBaseTest implements NotificationServiceTestBase {

    @Autowired
    protected NotificationService notificationService;

    @Override
    @Test
    public void testFindNotificationById() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testCreateNotification() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testUpdateNotification() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testDeleteNotification() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testFindAllNotification() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testFindNotificationByCondition() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void testRetrieveNotificationByUrn() throws Exception {
        // TODO Auto-generated method stub

    }
}
