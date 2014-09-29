package org.siemac.metamac.notices.core.mapper;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.domain.Notice;
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
    public void testNotificationWithResources() throws MetamacException {
        Notice notice = NoticeMockFactory.getNotification03WithResources();
        NoticeDto actual = mapper.noticeDoToDto(notice);
        assertEquals(notice.getId(), actual.getId());
        assertEquals(notice.getUrn(), actual.getUrn());
        assertEquals(notice.getVersion(), actual.getVersion());
        assertEquals(notice.getNoticeType(), actual.getType());
        assertEquals(notice.getSubject(), actual.getSubject());
        assertEquals(notice.getCreatedDate().toDate(), actual.getCreationDate());
        assertEquals(notice.getSendingApplication(), actual.getSendingApplication());
        assertEquals(notice.getSendingUser(), actual.getSendingUser());
        assertEquals(notice.getMessages().size(), actual.getMessages().size());
    }

    @Test
    public void testAnnouncement() throws MetamacException {
        Notice notice = NoticeMockFactory.getAnnouncement01();
        NoticeDto actual = mapper.noticeDoToDto(notice);
        assertEquals(notice.getNoticeType(), actual.getType());
        assertEquals(notice.getExpirationDate().toDate(), actual.getExpirationDate());
    }

    @Test
    public void testNullNotice() throws MetamacException {
        Notice notice = null;
        NoticeDto dto = mapper.noticeDoToDto(notice);
        assertNull(dto);
    }
}
