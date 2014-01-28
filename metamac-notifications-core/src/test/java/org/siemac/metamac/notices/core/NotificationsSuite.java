package org.siemac.metamac.notifications.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.notifications.core.channel.mail.serviceimpl.MailChannelServiceTest;
import org.siemac.metamac.notifications.core.common.repositoryimpl.InternationalStringRepositoryTest;
import org.siemac.metamac.notifications.core.notice.repositoryimpl.NotificationRepositoryTest;
import org.siemac.metamac.notifications.core.notice.serviceapi.NotificationServiceTest;

// @formatter:off
@RunWith(Suite.class)
@Suite.SuiteClasses({MailChannelServiceTest.class,
                        InternationalStringRepositoryTest.class,
                        NotificationRepositoryTest.class,
                        NotificationServiceTest.class})
// @formatter:on
public class NotificationsSuite {
}
