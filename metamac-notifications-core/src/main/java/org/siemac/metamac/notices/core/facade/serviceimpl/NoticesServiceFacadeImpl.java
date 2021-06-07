package org.siemac.metamac.notices.core.facade.serviceimpl;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.criteria.mapper.MetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.notices.core.criteria.mapper.SculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.notices.core.dto.NoticeCreationResultDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.mapper.NoticeDo2DtoMapper;
import org.siemac.metamac.notices.core.mapper.NoticeDto2DoMapper;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeCreationResult;
import org.siemac.metamac.notices.core.notice.serviceimpl.util.NoticesServiceUtil;
import org.siemac.metamac.notices.core.security.NoticesSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of NoticesServiceFacade.
 */
@Service("noticesServiceFacade")
public class NoticesServiceFacadeImpl extends NoticesServiceFacadeImplBase {

    @Autowired
    private MetamacCriteria2SculptorCriteriaMapper metamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private SculptorCriteria2MetamacCriteriaMapper sculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private NoticeDto2DoMapper                     dto2DoMapper;

    @Autowired
    private NoticeDo2DtoMapper                     do2DtoMapper;

    public NoticesServiceFacadeImpl() {
    }

    @Override
    public NoticeDto retrieveNoticeByUrn(ServiceContext ctx, String urn) throws MetamacException {

        // Security
        NoticesSecurityUtils.canRetrieveNotice(ctx);

        // Retrieve
        Notice notice = getNoticesService().retrieveNoticeByUrn(ctx, urn);

        // Transform
        return do2DtoMapper.noticeDoToDto(notice);
    }

    @Override
    public MetamacCriteriaResult<NoticeDto> findNotices(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {

        // Security
        NoticesSecurityUtils.canFindNotices(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = metamacCriteria2SculptorCriteriaMapper.getNoticeCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<Notice> result = getNoticesService().findNoticeByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        return sculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultNoticeDto(result, sculptorCriteria.getPageSize());
    }

    @Override
    public void markNoticeAsRead(ServiceContext ctx, String noticeUrn, String username) throws MetamacException {

        // Security
        NoticesSecurityUtils.canMarkNoticeAsRead(ctx);

        // Mark as read
        getNoticesService().markNoticeForReceiverAsRead(ctx, noticeUrn, username);
    }

    @Override
    public void markNoticeAsUnread(ServiceContext ctx, String noticeUrn, String username) throws MetamacException {

        // Security
        NoticesSecurityUtils.canMarkNoticeAsUnread(ctx);

        // Mark as unread
        getNoticesService().markNoticeForReceiverAsUnread(ctx, noticeUrn, username);
    }

    @Override
    public NoticeCreationResultDto sendAnnouncement(ServiceContext ctx, NoticeDto noticeDto) throws MetamacException {

        // Security
        NoticesSecurityUtils.canSendAnnouncement(ctx);

        // Transform
        Notice notice = dto2DoMapper.noticeDtoToDo(noticeDto);

        // Create
        NoticeCreationResult noticeCreationResult = new NoticeCreationResult();
        if(NoticesServiceUtil.isExternalUser(noticeDto)){
            noticeCreationResult = getNoticesService().createNoticeForExternalUsers(ctx, notice);
        }else{
            noticeCreationResult = getNoticesService().createNotice(ctx, notice);
        }


        return do2DtoMapper.noticeCreationResultDo2Dto(noticeCreationResult);
    }
}
