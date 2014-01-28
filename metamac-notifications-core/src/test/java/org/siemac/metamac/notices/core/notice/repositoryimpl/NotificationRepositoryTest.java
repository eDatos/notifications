package org.siemac.metamac.notifications.core.notice.repositoryimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notifications.core.NotificationsBaseTest;
import org.siemac.metamac.notifications.core.notice.domain.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notifications/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class NotificationRepositoryTest extends NotificationsBaseTest implements NotificationRepositoryTestBase {

    @Autowired
    protected NotificationRepository notificationRepository;

    @Override
    @Test
    public void testRetrieveByUrn() throws Exception {
        // TODO "testRetrieveByUrn not implemented"
    }
}
