package org.siemac.metamac.rest.notifications.v1_0.service;

import static org.mockito.Matchers.any;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.serviceapi.NotificationService;
import org.siemac.metamac.notifications.rest.v1_0.service.NotificationsV1_0;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.siemac.metamac.rest.notifications.v1_0.utils.RestDoMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public abstract class NotificationsRestExternalFacadeV10BaseTest extends MetamacRestBaseTest {

    protected static Logger             logger             = LoggerFactory.getLogger(NotificationsRestExternalFacadeV10BaseTest.class);

    private static String               jaxrsServerAddress = "http://localhost:" + ServerResource.PORT + "/apis/notifications";
    protected String                    baseApi            = jaxrsServerAddress + "/v1.0";
    protected static ApplicationContext applicationContext = null;
    protected static NotificationsV1_0  notificationsRestExternalFacadeClientXml;
    private static String               apiEndpointv10;

    protected static RestDoMocks        restDoMocks;

    private NotificationService         notificationService;

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

    protected void incrementRequestTimeOut(WebClient create) {
        ClientConfiguration config = WebClient.getConfig(create);
        HTTPConduit conduit = config.getHttpConduit();
        conduit.getClient().setConnectionTimeout(3000000);
        conduit.getClient().setReceiveTimeout(7000000);
        conduit.getClient().setConnection(ConnectionType.CLOSE);
    }

    protected void assertInputStream(InputStream expected, InputStream actual, boolean onlyPrint) throws IOException {
        byte[] byteArray = IOUtils.toByteArray(actual);
        if (logger.isDebugEnabled()) {
            System.out.println("-------------------");
            System.out.println(IOUtils.toString(new ByteArrayInputStream(byteArray)));
            System.out.println("-------------------");
        }
        if (!onlyPrint) {
            MetamacRestAsserts.assertEqualsResponse(expected, new ByteArrayInputStream(byteArray));
        }
    }
    private void mockRetrieveNotificationByUrn() throws MetamacException {
        when(notificationService.retrieveNotificationByUrn(any(ServiceContext.class), any(String.class))).thenAnswer(new Answer<Notification>() {

            @Override
            public Notification answer(InvocationOnMock invocation) throws Throwable {
                String urn = (String) invocation.getArguments()[1];
                return RestDoMocks.mockNotification_TYPE_NOTIFICATION(urn);
            };
        });
    }

    private void resetMocks() throws Exception {
        notificationService = applicationContext.getBean(NotificationService.class);
        reset(notificationService);

        mockRetrieveNotificationByUrn();
    }

}