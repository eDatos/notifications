package org.siemac.metamac.notices.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeAction;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeResult;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateNoticeRecieverAcknowledgeActionHandler extends SecurityActionHandler<UpdateNoticeRecieverAcknowledgeAction, UpdateNoticeRecieverAcknowledgeResult> {

    public UpdateNoticeRecieverAcknowledgeActionHandler() {
        super(UpdateNoticeRecieverAcknowledgeAction.class);
    }

    @Override
    public UpdateNoticeRecieverAcknowledgeResult executeSecurityAction(UpdateNoticeRecieverAcknowledgeAction action) throws ActionException {

        try {
            MetamacPrincipal metamacPrincipal = SecurityUtils.getMetamacPrincipal(ServiceContextHolder.getCurrentServiceContext());
            String username = metamacPrincipal.getUserId();

            // TODO METAMAC-1984

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        return new UpdateNoticeRecieverAcknowledgeResult();
    }
}
