package org.siemac.metamac.notices.web.server.handlers;

import static org.siemac.metamac.notices.core.notice.enume.domain.NoticeType.ANNOUNCEMENT;
import static org.siemac.metamac.notices.core.notice.enume.domain.NoticeType.NOTIFICATION;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.shared.GetNoticesAction;
import org.siemac.metamac.notices.web.shared.GetNoticesResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;

import com.gwtplatform.dispatch.shared.ActionException;

public class GetNoticesActionHandler extends SecurityActionHandler<GetNoticesAction, GetNoticesResult> {

    public GetNoticesActionHandler() {
        super(GetNoticesAction.class);
    }

    @Override
    public GetNoticesResult executeSecurityAction(GetNoticesAction action) throws ActionException {
        // TODO METAMAC-1984

        List<NoticeDto> notices = new ArrayList<NoticeDto>();

        NoticeDto n1 = new NoticeDto();
        n1.setSendingApplication("GESTOR_RECURSOS_ESTADÍSTICOS");
        n1.setSendingUser("lgutmed");
        n1.setSubject("asunto de notificación");
        n1.setType(NOTIFICATION);
        notices.add(n1);

        NoticeDto n2 = new NoticeDto();
        n2.setExpirationDate(new Date());
        n2.setSendingUser("announcement_user");
        n2.setSubject("asunto de anuncio");
        n2.setType(ANNOUNCEMENT);
        notices.add(n2);

        return new GetNoticesResult(notices, 0, 2);
    }
}
