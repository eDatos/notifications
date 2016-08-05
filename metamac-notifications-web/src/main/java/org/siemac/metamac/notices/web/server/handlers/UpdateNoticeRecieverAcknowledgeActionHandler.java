package org.siemac.metamac.notices.web.server.handlers;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.facade.serviceapi.NoticesServiceFacade;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeAction;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeResult;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateNoticeRecieverAcknowledgeActionHandler extends SecurityActionHandler<UpdateNoticeRecieverAcknowledgeAction, UpdateNoticeRecieverAcknowledgeResult> {

    @Autowired
    private NoticesServiceFacade noticesServiceFacade;

    public UpdateNoticeRecieverAcknowledgeActionHandler() {
        super(UpdateNoticeRecieverAcknowledgeAction.class);
    }

    @Override
    public UpdateNoticeRecieverAcknowledgeResult executeSecurityAction(UpdateNoticeRecieverAcknowledgeAction action) throws ActionException {

        String username = null;
        ServiceContext ctx = ServiceContextHolder.getCurrentServiceContext();

        try {
            MetamacPrincipal metamacPrincipal = SecurityUtils.getMetamacPrincipal(ServiceContextHolder.getCurrentServiceContext());
            username = metamacPrincipal.getUserId();
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        MetamacException mainMetamacException = new MetamacException();

        for (NoticeDto notice : action.getNotices()) {
            try {
                if (action.isAcknowledgeStatus()) {
                    noticesServiceFacade.markNoticeAsRead(ctx, notice.getUrn(), username);
                } else {
                    noticesServiceFacade.markNoticeAsUnread(ctx, notice.getUrn(), username);
                }
            } catch (MetamacException e) {
                mainMetamacException.getExceptionItems().addAll(e.getExceptionItems());
            }
        }

        if (mainMetamacException.getExceptionItems() != null && !mainMetamacException.getExceptionItems().isEmpty()) {
            throw WebExceptionUtils.createMetamacWebException(mainMetamacException);
        }

        return new UpdateNoticeRecieverAcknowledgeResult();
    }
}
