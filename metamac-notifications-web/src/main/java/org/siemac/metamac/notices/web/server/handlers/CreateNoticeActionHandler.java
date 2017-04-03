package org.siemac.metamac.notices.web.server.handlers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.dto.NoticeCreationResultDto;
import org.siemac.metamac.notices.core.facade.serviceapi.NoticesServiceFacade;
import org.siemac.metamac.notices.web.shared.CreateNoticeAction;
import org.siemac.metamac.notices.web.shared.CreateNoticeResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class CreateNoticeActionHandler extends SecurityActionHandler<CreateNoticeAction, CreateNoticeResult> {

    @Autowired
    private NoticesServiceFacade noticesServiceFacade;

    public CreateNoticeActionHandler() {
        super(CreateNoticeAction.class);
    }

    @Override
    public CreateNoticeResult executeSecurityAction(CreateNoticeAction action) throws ActionException {
        try {
            NoticeCreationResultDto noticeCreationResultDto = noticesServiceFacade.sendAnnouncement(ServiceContextHolder.getCurrentServiceContext(), action.getNotice());
            return new CreateNoticeResult(noticeCreationResultDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
