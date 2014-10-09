package org.siemac.metamac.notices.core.channel.mail.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

        mailChannelService.sendMail(getServiceContextAdministrador(), notice, new String[]{"mailTo@domain.com"}, "replyTo@domain.com");

        assertEquals(1, greenMail.getReceivedMessages().length);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);

        assertEquals("Test subject", messages[0].getSubject());
        assertEquals("replyTo@domain.com", ((InternetAddress) messages[0].getReplyTo()[0]).getAddress());
        assertEquals("mailTo@domain.com", ((InternetAddress) messages[0].getRecipients(RecipientType.TO)[0]).getAddress());

        Object content = messages[0].getContent();
        assertTrue(content instanceof MimeMultipart);

        MimeMultipart multiPart = (MimeMultipart) content;
        assertEquals(1, multiPart.getCount());
    }
}
