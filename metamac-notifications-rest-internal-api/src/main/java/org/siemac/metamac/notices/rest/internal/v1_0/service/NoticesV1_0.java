package org.siemac.metamac.notices.rest.internal.v1_0.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.siemac.metamac.rest.notices.v1_0.domain.Notice;

@Path("v1.0")
// IMPORTANT: If a new version of API is added, remember change latest url y urlrewrite.xml in war
public interface NoticesV1_0 {

    @PUT
    @Consumes({"application/xml"})
    @Produces({"application/xml"})
    @Path("notices")
    Response createNotice(Notice notice);

    @GET
    @Produces({"application/xml"})
    @Path("notices/{urn}")
    Notice retrieveNoticeByUrn(@PathParam("urn") String urn);
}
