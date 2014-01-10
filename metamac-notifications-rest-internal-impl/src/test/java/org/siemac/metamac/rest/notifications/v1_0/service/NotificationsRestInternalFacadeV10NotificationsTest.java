package org.siemac.metamac.rest.notifications.v1_0.service;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.siemac.metamac.rest.notifications.v1_0.utils.NotificationsRestMocks;

public class NotificationsRestInternalFacadeV10NotificationsTest extends NotificationsRestInternalFacadeV10BaseTest {

    @Test
    public void test_PUT_Notification() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi);
            incrementRequestTimeOut(create); // Timeout
            create.path("notifications");
            Response response = create.put(NotificationsRestMocks.mockNotification_TYPE_NOTIFICATION());

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void test_GET_Notification() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi);
            incrementRequestTimeOut(create); // Timeout
            create.path("notifications/{0}", NOTIFICATION_URN);
            Response response = create.get();

            InputStream responseExpected = NotificationsRestInternalFacadeV10NotificationsTest.class.getResourceAsStream("/responses/notifications/retrieveNotification.xml");
            assertEquals(200, response.getStatus());
            assertInputStream(responseExpected, (InputStream) response.getEntity(), true);
        }
    }
}