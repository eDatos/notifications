package org.siemac.metamac.notices.web.server.handlers;

import static org.siemac.metamac.notices.core.notice.enume.domain.NoticeType.ANNOUNCEMENT;
import static org.siemac.metamac.notices.core.notice.enume.domain.NoticeType.NOTIFICATION;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.web.shared.GetNoticesAction;
import org.siemac.metamac.notices.web.shared.GetNoticesResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetNoticesActionHandler extends SecurityActionHandler<GetNoticesAction, GetNoticesResult> {

    public GetNoticesActionHandler() {
        super(GetNoticesAction.class);
    }

    @Override
    public GetNoticesResult executeSecurityAction(GetNoticesAction action) throws ActionException {
        // TODO METAMAC-1984

        List<NoticeDto> notices = new ArrayList<NoticeDto>();

        for (int i = 0; i < 10; i++) {
            NoticeDto n1 = new NoticeDto();
            n1.setId(1L);
            n1.setSendingApplication("GESTOR_RECURSOS_ESTADISTICOS");
            n1.setSendingUser("lgutmed");
            n1.setSubject("asunto de notificación");
            n1.setType(NOTIFICATION);

            ReceiverDto r1 = new ReceiverDto();
            r1.setId(1L);
            r1.setUsername("METAMAC_ADMIN");
            r1.setAcknowledge(true);
            n1.addReceiver(r1);

            ReceiverDto r2 = new ReceiverDto();
            r2.setId(2L);
            r2.setUsername("OTHER_USER");
            r2.setAcknowledge(true);
            n1.addReceiver(r2);

            notices.add(n1);

            NoticeDto n2 = new NoticeDto();
            n2.setId(2L);
            n2.setExpirationDate(new Date());
            n2.setSendingUser("announcement_user");
            n2.setSubject("asunto de anuncio");
            n2.setType(ANNOUNCEMENT);
            notices.add(n2);

            ReceiverDto r3 = new ReceiverDto();
            r3.setId(3L);
            r3.setUsername("METAMAC_ADMIN");
            r3.setAcknowledge(false);
            n2.addReceiver(r3);
        }

        return new GetNoticesResult(notices, 0, 20);
    }
}
