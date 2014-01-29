package org.siemac.metamac.notices.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.notices.core.channel.mail.serviceimpl.MailChannelServiceTest;
import org.siemac.metamac.notices.core.common.repositoryimpl.InternationalStringRepositoryTest;
import org.siemac.metamac.notices.core.notice.repositoryimpl.NoticeRepositoryTest;
import org.siemac.metamac.notices.core.notice.serviceapi.NoticesServiceTest;

// @formatter:off
@RunWith(Suite.class)
@Suite.SuiteClasses({MailChannelServiceTest.class,
                        InternationalStringRepositoryTest.class,
                        NoticeRepositoryTest.class,
                        NoticesServiceTest.class})
// @formatter:on
public class NoticesSuite {
}