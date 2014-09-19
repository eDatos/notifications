package org.siemac.metamac.notices.core.notice.serviceapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeRepository;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.ReceiverRepository;
import org.siemac.metamac.notices.core.utils.builders.NoticeBuilder;
import org.siemac.metamac.notices.core.utils.mocks.factories.NoticeMockFactory;
import org.siemac.metamac.notices.core.utils.mocks.templates.NoticesDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class NoticesServiceTest extends NoticesBaseTest implements NoticesServiceTestBase {

    @Autowired
    protected NoticesService   noticeService;

    @Autowired
    private NoticeRepository   noticeRepository;

    @Autowired
    private ReceiverRepository receiverRepository;

    @Override
    @Test
    public void testFindNoticeById() throws Exception {
        // TODO: Implement (METAMAC-2147)

    }

    @Override
    @Test
    public void testCreateNotice() throws Exception {
        Notice notice = NoticesDoMocks.mockNoticeWithoutResources();
        Notice persitedNotice = noticeService.createNotice(getServiceContextWithoutPrincipal(), notice);
        assertNotNull(persitedNotice);
    }

    @Override
    @Test
    public void testUpdateNotice() throws Exception {
        // FIXME: No se testea porque updateNotice no se está usando. Si cuando el core esté estable sigue sin usarse, borrar
        // método del servicio y borrar test
    }

    @Override
    @Test
    public void testDeleteNotice() throws Exception {
        // FIXME: No se testea porque updateNotice no se está usando. Si cuando el core esté estable sigue sin usarse, borrar
        // método del servicio y borrar test
    }

    @Override
    @Test
    public void testFindAllNotice() throws Exception {
        // TODO: Implement (METAMAC-2147)

    }

    @Override
    @Test
    public void testFindNoticeByCondition() throws Exception {
        // TODO: Implement (METAMAC-2147)

    }

    @Override
    @Test
    public void testRetrieveNoticeByUrn() throws Exception {
        // TODO: Implement (METAMAC-2147)

    }

    @Override
    @Test
    public void testFindUserNotices() throws Exception {
        // TODO: Implement (METAMAC-2147)
    }

    @Override
    @Test
    public void testMarkNoticeForReceiverAsRead() throws Exception {
        // Constants
        String noticeUrn = NoticeMockFactory.NOTIFICATION_01_URN;
        String username = NoticeMockFactory.NOTICE_USER_1;

        // Prepare test
        Notice notice01 = NoticeBuilder.notification().withReceivers(NoticeMockFactory.NOTICE_USER_1).withUrn(NoticeMockFactory.NOTIFICATION_01_URN).build();
        noticeRepository.save(notice01);
        Notice notice02 = NoticeBuilder.notification().withReceivers(NoticeMockFactory.NOTICE_USER_2).withUrn(NoticeMockFactory.NOTIFICATION_02_URN).build();
        noticeRepository.save(notice02);
        Notice notice03 = NoticeBuilder.notification().withReceivers(NoticeMockFactory.NOTICE_USER_3).withUrn(NoticeMockFactory.NOTIFICATION_03_URN).build();
        noticeRepository.save(notice03);
        Notice notice04 = NoticeBuilder.notification().withReceivers(NoticeMockFactory.NOTICE_USER_1, NoticeMockFactory.NOTICE_USER_4).withUrn(NoticeMockFactory.NOTIFICATION_04_URN).build();
        noticeRepository.save(notice04);

        // Test
        Receiver receiverBefore = receiverRepository.retrieveReceiver(noticeUrn, username);
        assertFalse(receiverBefore.isAcknowledge());

        noticeService.markNoticeForReceiverAsRead(getServiceContextWithoutPrincipal(), noticeUrn, username);

        Receiver receiverAfter = receiverRepository.retrieveReceiver(noticeUrn, username);
        assertTrue(receiverAfter.isAcknowledge());
    }

    @Override
    @Test
    public void testRetrieveReceiver() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testUpdateReceiver() throws Exception {
        // TODO Auto-generated method stub

    }

}
