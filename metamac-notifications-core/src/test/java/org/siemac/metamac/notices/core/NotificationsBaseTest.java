package org.siemac.metamac.notices.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.siemac.metamac.common.test.MetamacBaseTest;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests.DataBaseProvider;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.notices.core.constants.NotificationsConstants;
import org.siemac.metamac.notices.core.utils.mocks.configuration.MockAnnotationRule;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class NotificationsBaseTest extends MetamacBaseTest {

    protected static Logger   logger          = LoggerFactory.getLogger(NotificationsBaseTest.class);

    protected static String   EMPTY           = StringUtils.EMPTY;

    protected static Long     ID_NOT_EXISTS   = Long.valueOf(-1);
    protected static String   URN_NOT_EXISTS  = "not_exists";
    protected static String   CODE_NOT_EXISTS = "NOT_EXISTS";

    @Value("${metamac.notifications.db.provider}")
    private String            databaseProvider;

    @Rule
    public MockAnnotationRule mockRule        = new MockAnnotationRule();

    @Rule
    public ExpectedException  thrown          = ExpectedException.none();

    protected ServiceContext getServiceContextWithoutPrincipal() {
        return mockServiceContextWithoutPrincipal();
    }

    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, "ADMINISTRADOR");
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, String rolName) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(rolName, NotificationsConstants.SECURITY_APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    @Override
    protected DataBaseProvider getDatabaseProvider() {
        return DataBaseProvider.valueOf(databaseProvider);
    }

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
