package org.siemac.metamac.statistical.resources.core;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.siemac.metamac.common.test.MetamacBaseTest;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests.DataBaseProvider;
import org.siemac.metamac.notifications.core.constants.NotificationsConstants;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockAnnotationRule;
import org.springframework.beans.factory.annotation.Value;

public abstract class NotificationsBaseTest extends MetamacBaseTest {

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

}
