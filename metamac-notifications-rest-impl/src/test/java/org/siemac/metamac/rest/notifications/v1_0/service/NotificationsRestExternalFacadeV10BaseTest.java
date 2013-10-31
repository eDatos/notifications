package org.siemac.metamac.rest.notifications.v1_0.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.notifications.rest.v1_0.service.NotificationsV1_0;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.notifications.v1_0.utils.RestDoMocks;
import org.springframework.context.ApplicationContext;

public abstract class NotificationsRestExternalFacadeV10BaseTest extends MetamacRestBaseTest {

    private static String               jaxrsServerAddress = "http://localhost:" + ServerResource.PORT + "/apis/notifications";
    protected String                    baseApi            = jaxrsServerAddress + "/v1.0";
    protected static ApplicationContext applicationContext = null;
    protected static NotificationsV1_0  notificationsRestExternalFacadeClientXml;
    private static String               apiEndpointv10;

    protected static RestDoMocks        restDoMocks;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();
        restDoMocks = new RestDoMocks();

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            notificationsRestExternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, NotificationsV1_0.class, providers, Boolean.TRUE);
        }
    }

    @Before
    public void setUp() throws Exception {
        ConfigurationService configurationService = applicationContext.getBean(ConfigurationService.class);
        apiEndpointv10 = configurationService.getProperty(ConfigurationConstants.ENDPOINT_NOTIFICATIONS_API) + "/v1.0";

        resetMocks();
    }

    @Test
    public void testErrorWithoutMatchError404() throws Exception {
        String requestUri = baseApi + "/nomatch";

        WebClient webClient = WebClient.create(requestUri).accept(APPLICATION_XML);
        Response response = webClient.get();

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        InputStream responseActual = (InputStream) response.getEntity();
        assertTrue(StringUtils.isBlank(IOUtils.toString(responseActual)));
    }

    protected NotificationsV1_0 getNotificationsRestExternalFacadeClientXml() {
        WebClient.client(notificationsRestExternalFacadeClientXml).reset();
        WebClient.client(notificationsRestExternalFacadeClientXml).accept(APPLICATION_XML);
        return notificationsRestExternalFacadeClientXml;
    }

    protected String getApiEndpoint() {
        return apiEndpointv10;
    }

    private void resetMocks() throws Exception {
    }
}