package org.siemac.metamac.notices.web.server.handlers;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.facade.serviceapi.NoticesServiceFacade;
import org.siemac.metamac.notices.web.shared.GetNoticeAction;
import org.siemac.metamac.notices.web.shared.GetNoticeResult;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetNoticeActionHandler extends SecurityActionHandler<GetNoticeAction, GetNoticeResult> {

    @Autowired
    private NoticesServiceFacade noticesServiceFacade;

    public GetNoticeActionHandler() {
        super(GetNoticeAction.class);
    }

    @Override
    public GetNoticeResult executeSecurityAction(GetNoticeAction action) throws ActionException {
        try {
            ServiceContext ctx = ServiceContextHolder.getCurrentServiceContext();
            MetamacPrincipal metamacPrincipal = SecurityUtils.getMetamacPrincipal(ServiceContextHolder.getCurrentServiceContext());
            String username = metamacPrincipal.getUserId();

            noticesServiceFacade.markNoticeAsRead(ctx, action.getNoticeUrn(), username);
            NoticeDto noticeDto = noticesServiceFacade.retrieveNoticeByUrn(ctx, action.getNoticeUrn());

            return new GetNoticeResult(noticeDto);

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
