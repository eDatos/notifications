package org.siemac.metamac.notices.web.server.handlers;

import static org.siemac.metamac.notices.core.notice.enume.domain.NoticeType.ANNOUNCEMENT;
import static org.siemac.metamac.notices.core.notice.enume.domain.NoticeType.NOTIFICATION;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.notices.core.dto.MessageDto;
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

        for (int i = 0; i < 5; i++) {
            NoticeDto n1 = new NoticeDto();
            n1.setCreationDate(new Date());
            n1.setId(1L);
            n1.setSendingApplication("GESTOR_RECURSOS_ESTADISTICOS");
            n1.setSendingUser("lgutmed");
            n1.setSubject("asunto de notificación");
            n1.setType(NOTIFICATION);
            n1.addMessage(createMessageDto());

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
            n2.setCreationDate(new Date());
            n2.setId(2L);
            n2.setExpirationDate(new Date());
            n2.setSendingUser("announcement_user");
            n2.setSubject("asunto de anuncio");
            n2.setType(ANNOUNCEMENT);
            n2.addMessage(createMessageDto());
            notices.add(n2);

            ReceiverDto r3 = new ReceiverDto();
            r3.setId(3L);
            r3.setUsername("METAMAC_ADMIN");
            r3.setAcknowledge(false);
            n2.addReceiver(r3);
        }

        return new GetNoticesResult(notices, 0, 10);
    }

    private MessageDto createMessageDto() {
        MessageDto m = new MessageDto();
        m.setText("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore "
                + "veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia "
                + "tationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse "
                + "quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? At vero eos et accusamus et iusto odio dignissimos ducimus "
                + "qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sun"
                + "t in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore,"
                + " cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere");
        m.addResource(createExternalItemDto());
        m.addResource(createExternalItemDto());
        m.addResource(createExternalItemDto());
        return m;
    }

    private ExternalItemDto createExternalItemDto() {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();
        localisedStringDto.setLabel("Índice censal de ocupación por plazas o por habitaciones según islas");
        localisedStringDto.setLocale("es");
        internationalStringDto.addText(localisedStringDto);
        externalItemDto.setTitle(internationalStringDto);
        externalItemDto.setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:C00031A_000001(005.004)");
        externalItemDto.setCode("C00031A_000001");
        externalItemDto.setManagementAppUrl("http://localhost:8080/metamac-statistical-resources-web/#operations/operation;id=C00031A/datasets/dataset;id=ISTAC:C00031A_000001(005.004)");
        externalItemDto.setUri("http://localhost:8080/metamac-statistical-resources-web/apis/statistical-resources-internal/latest/datasets/ISTAC/C00031A_000001/005.004");
        return externalItemDto;
    }
}
