package org.siemac.metamac.notices.core.notice.repositoryimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.notice.domain.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class NoticeRepositoryTest extends NoticesBaseTest implements NoticeRepositoryTestBase {

    @Autowired
    protected NoticeRepository noticeRepository;

    @Override
    @Test
    public void testRetrieveByUrn() throws Exception {
        // TODO "testRetrieveByUrn not implemented" (METAMAC-2147)
    }
}
