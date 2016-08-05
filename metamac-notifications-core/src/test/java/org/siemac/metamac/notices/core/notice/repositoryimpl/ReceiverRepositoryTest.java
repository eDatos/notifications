package org.siemac.metamac.notices.core.notice.repositoryimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeRepository;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.ReceiverRepository;
import org.siemac.metamac.notices.core.utils.mocks.factories.NoticeMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ReceiverRepositoryTest extends NoticesBaseTest implements ReceiverRepositoryTestBase {

    @Autowired
    protected ReceiverRepository receiverRepository;

    @Autowired
    private NoticeRepository     noticeRepository;

    @Override
    @Test
    public void testRetrieveReceiver() throws Exception {
        // Constants
        String noticeUrn = NoticeMockFactory.NOTIFICATION_02_URN;
        String username = NoticeMockFactory.NOTICE_USER_1;

        // Prepare test
        Notice notice02 = NoticeMockFactory.getNotification02WithReceivers();
        noticeRepository.save(notice02);
        Notice notice03 = NoticeMockFactory.getNotification03WithResources();
        noticeRepository.save(notice03);

        // Test
        Receiver actualReceiver = receiverRepository.retrieveReceiver(noticeUrn, username);
        assertEquals(username, actualReceiver.getUsername());
        assertEquals(noticeUrn, actualReceiver.getNotice().getUrn());
    }

}
