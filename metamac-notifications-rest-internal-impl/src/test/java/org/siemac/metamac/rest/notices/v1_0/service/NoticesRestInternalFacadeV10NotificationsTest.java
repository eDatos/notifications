package org.siemac.metamac.rest.notices.v1_0.service;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.siemac.metamac.notices.core.utils.mocks.factories.NoticeMockFactory;
import org.siemac.metamac.rest.notices.v1_0.utils.NoticesRestMocks;

public class NoticesRestInternalFacadeV10NotificationsTest extends NoticesRestInternalFacadeV10BaseTest {

    @Test
    public void test_PUT_Notice() throws Exception {

        { // All data: specific with general format (StructureSpecificData)
            WebClient create = WebClient.create(baseApi);
            incrementRequestTimeOut(create); // Timeout
            create.path("notices");
            Response response = create.put(NoticesRestMocks.mockNotice_TYPE_NOTIFICATION());

            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        }
    }

    @Test
    public void test_GET_NoticeByConditions() throws Exception {
        {
            WebClient create = WebClient.create(baseApi);
            incrementRequestTimeOut(create); // Timeout
            create.path("notices/{0}", NoticeMockFactory.NOTIFICATION_01_URN);
            Response response = create.get();

            InputStream responseExpected = NoticesRestInternalFacadeV10NotificationsTest.class.getResourceAsStream("/responses/notifications/retrieveNotification-01-byConditions.xml");
            assertEquals(200, response.getStatus());
            assertInputStream(responseExpected, (InputStream) response.getEntity(), false);
        }
    }

    @Test
    public void test_GET_NoticeByReceivers() throws Exception {
        {
            WebClient create = WebClient.create(baseApi);
            incrementRequestTimeOut(create); // Timeout
            create.path("notices/{0}", NoticeMockFactory.NOTIFICATION_02_URN);
            Response response = create.get();

            InputStream responseExpected = NoticesRestInternalFacadeV10NotificationsTest.class.getResourceAsStream("/responses/notifications/retrieveNotification-02-byReceivers.xml");
            assertEquals(200, response.getStatus());
            assertInputStream(responseExpected, (InputStream) response.getEntity(), false);
        }
    }

    @Test
    public void test_GET_NoticeWithResources() throws Exception {
        {
            WebClient create = WebClient.create(baseApi);
            incrementRequestTimeOut(create); // Timeout
            create.path("notices/{0}", NoticeMockFactory.NOTIFICATION_03_URN);
            Response response = create.get();

            InputStream responseExpected = NoticesRestInternalFacadeV10NotificationsTest.class.getResourceAsStream("/responses/notifications/retrieveNotification-03-withResources.xml");
            assertEquals(200, response.getStatus());
            assertInputStream(responseExpected, (InputStream) response.getEntity(), false);
        }
    }

    @Test
    public void test_GET_NoticeErrorNotExists() throws Exception {
        {
            WebClient create = WebClient.create(baseApi);
            incrementRequestTimeOut(create); // Timeout
            create.path("notices/{0}", NOT_EXISTS);
            Response response = create.get();

            InputStream responseExpected = NoticesRestInternalFacadeV10NotificationsTest.class.getResourceAsStream("/responses/notifications/retrieveNotification-04-notExists.xml");
            assertEquals(404, response.getStatus());
            assertInputStream(responseExpected, (InputStream) response.getEntity(), false);
        }
    }
}