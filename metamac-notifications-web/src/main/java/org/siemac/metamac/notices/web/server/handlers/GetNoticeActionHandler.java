package org.siemac.metamac.notices.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.web.shared.GetNoticeAction;
import org.siemac.metamac.notices.web.shared.GetNoticeResult;
import org.siemac.metamac.notices.web.shared.utils.CommonSharedUtils;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetNoticeActionHandler extends SecurityActionHandler<GetNoticeAction, GetNoticeResult> {

    public GetNoticeActionHandler() {
        super(GetNoticeAction.class);
    }

    @Override
    public GetNoticeResult executeSecurityAction(GetNoticeAction action) throws ActionException {

        try {
            MetamacPrincipal metamacPrincipal = SecurityUtils.getMetamacPrincipal(ServiceContextHolder.getCurrentServiceContext());
            String username = metamacPrincipal.getUserId();

            ReceiverDto receiverDto = CommonSharedUtils.getCurrentReceiverFromNotice(action.getNotice(), username);
            receiverDto.setAcknowledge(true);

            // TODO METAMAC-1984 update notice

            return new GetNoticeResult(action.getNotice());

        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
