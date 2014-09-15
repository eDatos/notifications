package org.siemac.metamac.notices.web.server.handlers;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.web.shared.GetUserGuideUrlAction;
import org.siemac.metamac.notices.web.shared.GetUserGuideUrlResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetUserGuideUrlActionHandler extends SecurityActionHandler<GetUserGuideUrlAction, GetUserGuideUrlResult> {

    public GetUserGuideUrlActionHandler() {
        super(GetUserGuideUrlAction.class);
    }

    @Override
    public GetUserGuideUrlResult executeSecurityAction(GetUserGuideUrlAction action) throws ActionException {
        // TODO METAMAC-1984
        return new GetUserGuideUrlResult(StringUtils.EMPTY);
    }
}
