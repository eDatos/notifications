package org.siemac.metamac.notices.web.server.handlers;

import org.siemac.metamac.notices.web.shared.CreateNoticeAction;
import org.siemac.metamac.notices.web.shared.CreateNoticeResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class CreateNoticeActionHandler extends SecurityActionHandler<CreateNoticeAction, CreateNoticeResult> {

    public CreateNoticeActionHandler() {
        super(CreateNoticeAction.class);
    }

    @Override
    public CreateNoticeResult executeSecurityAction(CreateNoticeAction action) throws ActionException {

        // TODO METAMAC-1984

        return new CreateNoticeResult(action.getNotice());
    }
}
