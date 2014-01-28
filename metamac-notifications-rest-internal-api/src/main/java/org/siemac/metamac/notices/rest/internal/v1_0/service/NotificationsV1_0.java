package org.siemac.metamac.notifications.rest.internal.v1_0.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.siemac.metamac.rest.notifications.v1_0.domain.Notification;

@Path("v1.0")
// IMPORTANT: If a new version of API is added, remember change latest url y urlrewrite.xml in war
public interface NotificationsV1_0 {

    @PUT
    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Path("notifications")
    Response createNotification(Notification notification);

    @GET
    @Produces({"application/xml"})
    @Path("notifications/{urn}")
    Notification retrieveResourceByUrn(@PathParam("urn") String urn);
}
