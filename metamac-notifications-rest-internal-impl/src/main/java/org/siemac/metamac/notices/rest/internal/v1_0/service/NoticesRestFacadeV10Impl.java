package org.siemac.metamac.notices.rest.internal.v1_0.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.siemac.metamac.notices.core.notice.serviceapi.NoticesService;
import org.siemac.metamac.notices.rest.internal.constants.NoticesRestConstants;
import org.siemac.metamac.notices.rest.internal.exception.NoticesRestServiceExceptionType;
import org.siemac.metamac.notices.rest.internal.service.utils.NoticesRestInternalUtils;
import org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice.NoticesDo2RestMapperV10;
import org.siemac.metamac.notices.rest.internal.v1_0.mapper.notice.NoticesRest2DoMapperV10;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noticesRestInternalFacadeV10")
public class NoticesRestFacadeV10Impl implements NoticesV1_0 {

    @Autowired
    private NoticesService          noticesService;

    @Autowired
    private NoticesRest2DoMapperV10 noticesRest2DoMapperV10;

    @Autowired
    private NoticesDo2RestMapperV10 noticesDo2RestMapperV10;

    @Override
    public Response createNotice(Notice notice) {
        try {
            // Transform
            org.siemac.metamac.notices.core.notice.domain.Notice noticeEntity = noticesRest2DoMapperV10.noticeRestToEntity(NoticesRestConstants.SERVICE_CONTEXT, notice);

            // Create
            noticesService.createNotice(NoticesRestConstants.SERVICE_CONTEXT, noticeEntity);

            return Response.status(Response.Status.CREATED).build();

        } catch (Exception e) {
            throw NoticesRestInternalUtils.manageException(e);
        }
    }

    @Override
    public Notice retrieveNoticeByUrn(String urn) {
        try {
            // Retrieve
            org.siemac.metamac.notices.core.notice.domain.Notice noticeEntity = noticesService.retrieveNoticeByUrn(NoticesRestConstants.SERVICE_CONTEXT, urn);

            if (noticeEntity == null) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(NoticesRestServiceExceptionType.NOTICE_NOT_FOUND, urn);
                throw new RestException(exception, Status.NOT_FOUND);
            }

            // Transform
            Notice noticeRest2Entity = noticesDo2RestMapperV10.noticeEntity2Rest(NoticesRestConstants.SERVICE_CONTEXT, noticeEntity);
            return noticeRest2Entity;
        } catch (Exception e) {
            throw NoticesRestInternalUtils.manageException(e);
        }
    }
}
