package org.siemac.metamac.notices.core.notice.serviceapi;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

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
        // TODO: Implement (METAMAC-2147)

    }

    @Override
    @Test
    @Ignore
    public void testCreateNotice() throws Exception {
        Notice notice = mockNotice();
        Notice persitedNotice = noticeService.createNotice(getServiceContextWithoutPrincipal(), notice);
        assertNotNull(persitedNotice);
    }

    @Override
    @Test
    public void testUpdateNotice() throws Exception {
        // TODO: Implement (METAMAC-2147)
    }

    @Override
    @Test
    public void testDeleteNotice() throws Exception {
        // TODO: Implement (METAMAC-2147)

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

    private static Notice mockNotice() {
        Notice notice = new Notice("test-app", "test-subject", NoticeType.NOTIFICATION);
        notice.addReceiver(mockReceiver(notice));
        notice.addMessage(mockMessage(notice));
        notice.setSendingUser("admin");
        return notice;
    }

    private static Receiver mockReceiver(Notice notice) {
        Receiver receiver = new Receiver();
        receiver.setUsername("admin");
        receiver.setAcknowledge(Boolean.FALSE);
        receiver.setNotice(notice);
        return receiver;
    }

    private static Message mockMessage(Notice notice) {
        Message message = new Message("Test message");
        message.setNotice(notice);
        return message;
    }

}
