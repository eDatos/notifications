package org.siemac.metamac.notices.core.notice.repositoryimpl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeRepository;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class NoticeRepositoryTest extends NoticesBaseTest implements NoticeRepositoryTestBase {

    @Autowired
    protected NoticeRepository noticeRepository;

    @Override
    @Test
    public void testRetrieveByUrn() throws Exception {
        String urn = GeneratorUrnUtils.generateSiemacNoticeUrn("UUID-01");

        // Prepare test
        Notice notice = NoticesDoMocks.mockNoticeWithoutResources();

        // We have to set URN because it is a field that fills the service.
        notice.setUrn(urn);
        notice = noticeRepository.save(notice);

        // Test

        Notice retrievedNotice = noticeRepository.retrieveByUrn(urn);
        assertEquals(urn, retrievedNotice.getUrn());
    }

    @Override
    @Test
    public void testFindByReceiverUsername() throws Exception {
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
        List<Notice> notices = noticeRepository.findByReceiverUsername("user-1");
        assertEquals(2, notices.size());

    }
}
