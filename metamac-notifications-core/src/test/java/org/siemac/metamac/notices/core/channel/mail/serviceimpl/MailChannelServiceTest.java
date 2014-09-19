package org.siemac.metamac.notices.core.channel.mail.serviceimpl;

import java.io.InputStream;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.notice.domain.Notice;
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
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml", "classpath:/spring/notices/include/more.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class MailChannelServiceTest extends NoticesBaseTest {

    @Autowired
    protected MailChannelService mailChannelService;

    @Test
    public void testMailChannel() throws Exception {
        Notice notice = NoticeMockFactory.getNotification03WithResources();

        mailChannelService.sendMail(getServiceContextAdministrador(), notice, new String[]{"count@domain.com"}, "count@domain.com");

        assertEquals(1, greenMail.getReceivedMessages().length);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);

        InputStream responseExpected = MailChannelServiceTest.class.getResourceAsStream("/responses/email/notice.mail");
        assertInputStream(responseExpected, IOUtils.toInputStream((String) messages[0].getContent()), false);
        assertEquals("Test subject", messages[0].getSubject());
    }
}
