package org.siemac.metamac.notices.web.server.handlers;

import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeAction;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class UpdateNoticeRecieverAcknowledgeActionHandler extends SecurityActionHandler<UpdateNoticeRecieverAcknowledgeAction, UpdateNoticeRecieverAcknowledgeResult> {

    public UpdateNoticeRecieverAcknowledgeActionHandler() {
        super(UpdateNoticeRecieverAcknowledgeAction.class);
    }

    @Override
    public UpdateNoticeRecieverAcknowledgeResult executeSecurityAction(UpdateNoticeRecieverAcknowledgeAction action) throws ActionException {

        // TODO METAMAC-1984

        return new UpdateNoticeRecieverAcknowledgeResult();
    }
}
