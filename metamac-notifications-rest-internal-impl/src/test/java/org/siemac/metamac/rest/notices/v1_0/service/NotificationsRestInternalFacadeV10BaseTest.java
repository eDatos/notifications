package org.siemac.metamac.rest.notifications.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.ConnectionType;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.notices.core.notice.serviceapi.NotificationService;
import org.siemac.metamac.notices.core.utils.mocks.factories.NotificationMockFactory;
import org.siemac.metamac.notices.rest.internal.v1_0.service.NoticesV1_0;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public abstract class NotificationsRestInternalFacadeV10BaseTest extends MetamacRestBaseTest {

    protected static Logger             logger             = LoggerFactory.getLogger(NotificationsRestInternalFacadeV10BaseTest.class);

    protected static String             NOT_EXISTS         = "NOT_EXISTS";

    private static String               jaxrsServerAddress = "http://localhost:" + ServerResource.PORT + "/apis/notifications-internal";
    protected String                    baseApi            = jaxrsServerAddress + "/v1.0";
    protected static ApplicationContext applicationContext = null;
    protected static NoticesV1_0        noticesRestInternalFacadeClientXml;
    private static String               apiEndpointv10;

    private NotificationService         notificationService;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            noticesRestInternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, NoticesV1_0.class, providers, Boolean.TRUE);
        }
    }

    @Before
    public void setUp() throws Exception {
        ConfigurationService configurationService = applicationContext.getBean(ConfigurationService.class);
        apiEndpointv10 = configurationService.retrieveNotificationsInternalApiUrlBase() + "/v1.0";

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

    protected NoticesV1_0 getNotificationsRestInternalFacadeClientXml() {
        WebClient.client(noticesRestInternalFacadeClientXml).reset();
        WebClient.client(noticesRestInternalFacadeClientXml).accept(APPLICATION_XML);
        return noticesRestInternalFacadeClientXml;
    }

    protected String getApiEndpoint() {
        return apiEndpointv10;
    }

    protected void incrementRequestTimeOut(WebClient create) {
        ClientConfiguration config = WebClient.getConfig(create);
        HTTPConduit conduit = config.getHttpConduit();
        conduit.getClient().setConnectionTimeout(3000000);
        conduit.getClient().setReceiveTimeout(7000000);
        conduit.getClient().setConnection(ConnectionType.CLOSE);
    }

    protected void assertInputStream(InputStream expected, InputStream actual, boolean onlyPrint) throws IOException {
        byte[] expectedByteArray = IOUtils.toByteArray(expected);
        byte[] actualByteArray = IOUtils.toByteArray(actual);

        if (logger.isDebugEnabled()) {
            systemShowInputStream(new ByteArrayInputStream(expectedByteArray), "EXPECTED");
            systemShowInputStream(new ByteArrayInputStream(actualByteArray), "ACTUAL");
        }
        if (!onlyPrint) {
            MetamacRestAsserts.assertEqualsResponse(new ByteArrayInputStream(expectedByteArray), new ByteArrayInputStream(actualByteArray));
        }
    }

    private void systemShowInputStream(InputStream inputStream, String name) throws IOException {
        System.out.println("-------------------");
        System.out.println(name);
        System.out.println("-------------------");
        System.out.println(IOUtils.toString(inputStream));
        System.out.println("-------------------");
    }

    private void mockRetrieveNotificationByUrn() throws MetamacException {
        when(notificationService.retrieveNotificationByUrn(any(ServiceContext.class), eq(NotificationMockFactory.NOTIFICATION_01_URN))).thenReturn(
                NotificationMockFactory.getNotification01WithConditions());
        when(notificationService.retrieveNotificationByUrn(any(ServiceContext.class), eq(NotificationMockFactory.NOTIFICATION_02_URN))).thenReturn(
                NotificationMockFactory.getNotification02WithReceivers());
        when(notificationService.retrieveNotificationByUrn(any(ServiceContext.class), eq(NotificationMockFactory.NOTIFICATION_03_URN))).thenReturn(
                NotificationMockFactory.getNotification03WithResources());
        when(notificationService.retrieveNotificationByUrn(any(ServiceContext.class), eq(NOT_EXISTS))).thenReturn(null);
    }

    private void resetMocks() throws Exception {
        notificationService = applicationContext.getBean(NotificationService.class);
        reset(notificationService);

        mockRetrieveNotificationByUrn();
    }

}