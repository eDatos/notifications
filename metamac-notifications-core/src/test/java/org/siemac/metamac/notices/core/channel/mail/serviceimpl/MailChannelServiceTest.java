package org.siemac.metamac.notices.core.channel.mail.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.security.Security;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.notices.core.NoticesBaseTest;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.utils.mocks.templates.NoticesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/notices/applicationContext-test.xml", "classpath:/spring/notices/include/more.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class MailChannelServiceTest extends NoticesBaseTest /* implements NoticeServiceTestBase */{

    @Autowired
    protected MailChannelService mailChannelService;

    private GreenMail            greenMail;

    @Before
    public void testSmtpInit() {
        // The SSL certificate used on the Greenmail server must be signed by a "known" certificate authority, OR you must add the CA certificate that was used to sign the cert to Java's keystore.
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());

        greenMail = new GreenMail(ServerSetupTest.SMTPS);
        greenMail.start();
    }

    // TODO: Cambiar el mock de este test por el mock 03 para que tenga varios mensajes y recursos asociados
    @Test
    public void testMailChannel() throws Exception {
        Notice notice = NoticesNotPersistedDoMocks.mockNoticeWithoutResources("urn:siemac:org.siemac.metamac.infomodel.notices.Notice=NOTICE_UUID", "application", "My message");

        mailChannelService.sendMail(getServiceContextAdministrador(), notice, new String[]{"count@domain.com"}, "count@domain.com");

        assertEquals(1, greenMail.getReceivedMessages().length);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);

        InputStream responseExpected = MailChannelServiceTest.class.getResourceAsStream("/responses/email/notice.mail");
        assertInputStream(responseExpected, IOUtils.toInputStream((String) messages[0].getContent()), false);
        assertEquals("Metamac Notification", messages[0].getSubject());
    }

    @After
    public void cleanup() {
        greenMail.stop();
    }
}
