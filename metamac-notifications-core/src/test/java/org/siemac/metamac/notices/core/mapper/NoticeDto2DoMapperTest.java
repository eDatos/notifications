package org.siemac.metamac.notices.core.mapper;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.utils.mocks.templates.NoticesDtoMocks;
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
public class NoticeDto2DoMapperTest extends NoticesBaseTest {

    @Autowired
    private NoticeDto2DoMapper mapper;

    @Test
    public void testAnnouncementDto2DoWithReceivers() throws MetamacException {
        NoticeDto expected = NoticesDtoMocks.mockAnnouncementWithReceivers();
        Notice actual = mapper.noticeDtoToDo(expected);
        assertEqualsNotice(expected, actual);
    }

    @Test
    public void testAnnouncementDto2DoWithConditions() throws MetamacException {
        NoticeDto expected = NoticesDtoMocks.mockAnnouncementWithConditions();
        Notice actual = mapper.noticeDtoToDo(expected);
        assertEqualsNotice(expected, actual);
    }

    // --------------------------------------------------------
    // PRIVATE UTILITY METHODS
    // --------------------------------------------------------

    private void assertEqualsNotice(NoticeDto expected, Notice actual) {
        assertEquals(expected.getSubject(), actual.getSubject());
        assertEquals(expected.getExpirationDate(), actual.getExpirationDate().toDate());
        assertEquals(expected.getSendingApplication(), actual.getSendingApplication());
        assertEquals(expected.getSendingUser(), actual.getSendingUser());
        assertEquals(expected.getType(), actual.getNoticeType());
        assertEqualsMessages(expected, actual);
        assertEqualsReceivers(expected, actual);
        assertEqualsRoles(expected, actual);
        assertEqualsStatisticalOperations(expected, actual);
        assertEqualsApplications(expected, actual);
    }

    private void assertEqualsMessages(NoticeDto expected, Notice actual) {
        assertEquals(expected.getMessages().size(), actual.getMessages().size());
        for (int i = 0; i < actual.getMessages().size(); i++) {
            assertEquals(expected.getMessages().get(i).getText(), actual.getMessages().get(i).getText());
        }
    }

    private void assertEqualsReceivers(NoticeDto expected, Notice actual) {
        assertEquals(expected.getReceivers().size(), actual.getReceivers().size());
        for (int i = 0; i < actual.getReceivers().size(); i++) {
            assertEqualsReceiver(expected.getReceivers().get(i), actual.getReceivers().get(i));
        }
    }

    private void assertEqualsReceiver(ReceiverDto expected, Receiver actual) {
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.isAcknowledge(), actual.isAcknowledge());
    }

    private void assertEqualsRoles(NoticeDto expected, Notice actual) {
        assertEquals(expected.getRoles().size(), actual.getRoles().size());
        for (int i = 0; i < actual.getRoles().size(); i++) {
            assertEquals(expected.getRoles().get(i), actual.getRoles().get(i).getName());
        }
    }

    private void assertEqualsStatisticalOperations(NoticeDto expected, Notice actual) {
        assertEquals(expected.getStatisticalOperationCodes().size(), actual.getStatisticalOperations().size());
        for (int i = 0; i < actual.getStatisticalOperations().size(); i++) {
            assertEquals(expected.getStatisticalOperationCodes().get(i), actual.getStatisticalOperations().get(i).getName());
        }
    }

    private void assertEqualsApplications(NoticeDto expected, Notice actual) {
        assertEquals(expected.getApplications().size(), actual.getApps().size());
        for (int i = 0; i < actual.getApps().size(); i++) {
            assertEquals(expected.getApplications().get(i), actual.getApps().get(i).getName());
        }
    }
}
