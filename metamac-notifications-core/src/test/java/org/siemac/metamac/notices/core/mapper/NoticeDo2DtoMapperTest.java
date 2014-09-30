package org.siemac.metamac.notices.core.mapper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.dto.MessageDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.utils.mocks.factories.NoticeMockFactory;
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
public class NoticeDo2DtoMapperTest extends NoticesBaseTest {

    @Autowired
    private NoticeDo2DtoMapper mapper;

    @Test
    public void testNotification() throws MetamacException {
        {
            Notice expected = NoticeMockFactory.getNotification01WithConditions();
            NoticeDto actual = mapper.noticeDoToDto(expected);
            assertEqualsNotice(expected, actual);
        }
        {
            Notice expected = NoticeMockFactory.getNotification02WithReceivers();
            NoticeDto actual = mapper.noticeDoToDto(expected);
            assertEqualsNotice(expected, actual);
        }
        {
            Notice expected = NoticeMockFactory.getNotification03WithResources();
            NoticeDto actual = mapper.noticeDoToDto(expected);
            assertEqualsNotice(expected, actual);
        }
    }

    @Test
    public void testAnnouncement() throws MetamacException {
        Notice expected = NoticeMockFactory.getAnnouncement01();
        NoticeDto actual = mapper.noticeDoToDto(expected);
        assertEqualsNotice(expected, actual);
    }

    @Test
    public void testNullNotice() throws MetamacException {
        Notice notice = null;
        NoticeDto dto = mapper.noticeDoToDto(notice);
        assertNull(dto);
    }

    // -------------------------------------------------------------------------------
    // PRIVATE UTILITY METHODS
    // -------------------------------------------------------------------------------

    private void assertEqualsNotice(Notice expected, NoticeDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getVersion(), actual.getVersion());
        assertEquals(expected.getNoticeType(), actual.getType());
        assertEquals(expected.getSubject(), actual.getSubject());
        assertEquals(expected.getCreatedDate().toDate(), actual.getCreationDate());
        assertEquals(expected.getSendingApplication(), actual.getSendingApplication());
        assertEquals(expected.getSendingUser(), actual.getSendingUser());

        assertEqualsMessages(expected, actual);
        assertEqualsReceivers(expected, actual);
        assertEqualsRoles(expected, actual);
        assertEqualsStatisticalOperations(expected, actual);
        assertEqualsApplications(expected, actual);
    }

    private void assertEqualsMessages(Notice expected, NoticeDto actual) {
        assertEquals(expected.getMessages().size(), actual.getMessages().size());
        for (int i = 0; i < actual.getMessages().size(); i++) {
            assertEqualsMessage(expected.getMessages().get(i), actual.getMessages().get(i));
        }
    }

    private void assertEqualsMessage(Message expected, MessageDto actual) {
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getResources().size(), actual.getResources().size());
        for (int i = 0; i < actual.getResources().size(); i++) {
            assertEquals(expected.getResources().get(i).getCode(), actual.getResources().get(i).getCode());
            assertEquals(expected.getResources().get(i).getUrn(), actual.getResources().get(i).getUrn());
            assertEquals(expected.getResources().get(i).getType(), actual.getResources().get(i).getType());
        }
    }

    private void assertEqualsReceivers(Notice expected, NoticeDto actual) {
        assertEquals(expected.getReceivers().size(), actual.getReceivers().size());
        for (int i = 0; i < actual.getReceivers().size(); i++) {
            assertEqualsReceiver(expected.getReceivers().get(i), actual.getReceivers().get(i));
        }
    }

    private void assertEqualsReceiver(Receiver expected, ReceiverDto actual) {
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.isAcknowledge(), actual.isAcknowledge());
    }

    private void assertEqualsRoles(Notice expected, NoticeDto actual) {
        assertEquals(expected.getRoles().size(), actual.getRoles().size());
        for (int i = 0; i < actual.getRoles().size(); i++) {
            assertEquals(expected.getRoles().get(i).getName(), actual.getRoles().get(i));
        }
    }

    private void assertEqualsStatisticalOperations(Notice expected, NoticeDto actual) {
        assertEquals(expected.getStatisticalOperations().size(), actual.getStatisticalOperationCodes().size());
        for (int i = 0; i < actual.getStatisticalOperationCodes().size(); i++) {
            assertEquals(expected.getStatisticalOperations().get(i).getName(), actual.getStatisticalOperationCodes().get(i));
        }
    }

    private void assertEqualsApplications(Notice expected, NoticeDto actual) {
        assertEquals(expected.getApps().size(), actual.getApplications().size());
        for (int i = 0; i < actual.getApplications().size(); i++) {
            assertEquals(expected.getApps().get(i).getName(), actual.getApplications().get(i));
        }
    }
}
