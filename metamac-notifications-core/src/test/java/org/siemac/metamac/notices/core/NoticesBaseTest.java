package org.siemac.metamac.notices.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Before;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

public abstract class NoticesBaseTest extends MetamacDBUnitBaseTests {

    protected static Logger logger          = LoggerFactory.getLogger(NoticesBaseTest.class);

    protected static String EMPTY           = StringUtils.EMPTY;

    protected static Long   ID_NOT_EXISTS   = Long.valueOf(-1);
    protected static String URN_NOT_EXISTS  = "not_exists";
    protected static String CODE_NOT_EXISTS = "NOT_EXISTS";

    @Value("${metamac.notices.db.provider}")
    private String          databaseProvider;

    protected GreenMail     greenMail;

    // --------------------------------------------------------------------------------------------------------------
    // CONFIGURE GREEN MAIL
    // --------------------------------------------------------------------------------------------------------------

    @Before
    public void testSmtpInit() {
        // The SSL certificate used on the Greenmail server must be signed by a "known" certificate authority, OR you must add the CA certificate that was used to sign the cert to Java's keystore.
        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());

        greenMail = new GreenMail(ServerSetupTest.SMTPS);
        greenMail.start();
    }

    @After
    public void cleanup() {
        greenMail.stop();
    }

    // --------------------------------------------------------------------------------------------------------------
    // SERVICE CONTEXT
    // --------------------------------------------------------------------------------------------------------------

    protected ServiceContext getServiceContextWithoutPrincipal() {
        return mockServiceContextWithoutPrincipal();
    }

    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        // TODO: Change ADMINISTRADOR for NoticesRoleEnum
        putMetamacPrincipalInServiceContext(serviceContext, "ADMINISTRADOR");
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, String rolName) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(rolName, NoticesConstants.SECURITY_APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    // --------------------------------------------------------------------------------------------------------------
    // DBUNIT CONFIGURATION
    // --------------------------------------------------------------------------------------------------------------

    @Override
    protected DataBaseProvider getDatabaseProvider() {
        return DataBaseProvider.valueOf(databaseProvider);
    }

    @Override
    protected String getDataSetFile() {
        return "dbunit/NoticesMocks.xml";
    }

    @Override
    protected List<String> getTableNamesOrderedByFKDependency() {
        List<String> tables = new ArrayList<String>();
        tables.add("TB_MESSAGE_RESOURCES");
        tables.add("TB_STATISTICAL_OPERATIONS");
        tables.add("TB_ROLES");
        tables.add("TB_RECEIVERS");
        tables.add("TB_MESSAGES");
        tables.add("TB_APPS");
        tables.add("TB_NOTICES");
        tables.add("TB_INTERNATIONAL_STRINGS");
        tables.add("TB_EXTERNAL_ITEMS");
        tables.add("TB_LOCALISED_STRINGS");
        return tables;
    }

    @Override
    protected Map<String, List<String>> getTablePrimaryKeys() {
        Map<String, List<String>> primaryKeys = new HashMap<String, List<String>>();
        primaryKeys.put("TB_SEQUENCES", Arrays.asList("SEQUENCE_NAME"));
        return primaryKeys;
    }

    // --------------------------------------------------------------------------------------------------------------
    // COMMON UTILITIES
    // --------------------------------------------------------------------------------------------------------------

    protected String buildCommaSeparatedString(String... items) {
        return StringUtils.join(items, ", ");
    }

    protected void assertInputStream(InputStream expected, InputStream actual, boolean onlyPrint) throws IOException {
        byte[] byteArray = IOUtils.toByteArray(actual);
        if (logger.isDebugEnabled()) {
            System.out.println("-------------------");
            System.out.println(IOUtils.toString(new ByteArrayInputStream(byteArray)));
            System.out.println("-------------------");
        }
        if (!onlyPrint) {
            MetamacAsserts.assertEqualsInputStream(expected, new ByteArrayInputStream(byteArray));
        }
    }
}
