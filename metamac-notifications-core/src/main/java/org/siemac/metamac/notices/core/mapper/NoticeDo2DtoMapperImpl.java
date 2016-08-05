package org.siemac.metamac.notices.core.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.common.domain.InternationalString;
import org.siemac.metamac.notices.core.common.domain.LocalisedString;
import org.siemac.metamac.notices.core.dto.MessageDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.core.notice.domain.App;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("noticeDo2DtoMapper")
public class NoticeDo2DtoMapperImpl implements NoticeDo2DtoMapper {

    @Autowired
    private BaseDo2DtoMapper baseDo2DtoMapper;

    @Override
    public NoticeDto noticeDoToDto(Notice source) throws MetamacException {
        if (source == null) {
            return null;
        }
        NoticeDto target = new NoticeDto();
        noticeDoToDto(source, target);
        return target;
    }

    private void noticeDoToDto(Notice source, NoticeDto target) throws MetamacException {
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setVersion(source.getVersion());
        target.setType(source.getNoticeType());
        target.setSubject(source.getSubject());
        target.setCreationDate(dateDoToDto(source.getCreatedDate()));
        target.setExpirationDate(dateDoToDto(source.getExpirationDate()));
        target.setSendingApplication(source.getSendingApplication());
        target.setSendingUser(source.getSendingUser());
        messagesDoToDto(source, target);
        receiversDoToDto(source, target);
        rolesDoToDto(source, target);
        statisticalOperationsDo2Dto(source, target);
        applicationsDo2Dto(source, target);
    }

    private void messagesDoToDto(Notice source, NoticeDto target) throws MetamacException {
        for (Message message : source.getMessages()) {
            target.addMessage(messageDoToDto(message));
        }
    }

    private void receiversDoToDto(Notice source, NoticeDto target) {
        for (Receiver receiver : source.getReceivers()) {
            target.addReceiver(receiverDo2Dto(receiver));
        }
    }

    private void rolesDoToDto(Notice source, NoticeDto target) {
        for (Role role : source.getRoles()) {
            target.getRoles().add(role.getName());
        }
    }

    private void statisticalOperationsDo2Dto(Notice source, NoticeDto target) {
        for (StatisticalOperation operation : source.getStatisticalOperations()) {
            target.getStatisticalOperationCodes().add(operation.getName());
        }
    }

    private void applicationsDo2Dto(Notice source, NoticeDto target) {
        for (App app : source.getApps()) {
            target.getApplications().add(app.getName());
        }
    }

    private MessageDto messageDoToDto(Message source) throws MetamacException {
        MessageDto target = new MessageDto();
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        target.setText(source.getText());

        for (ExternalItem externalItem : source.getResources()) {
            target.addResource(externalItemToDto(externalItem));
        }

        return target;
    }

    private ReceiverDto receiverDo2Dto(Receiver source) {
        ReceiverDto target = new ReceiverDto();
        target.setId(source.getId());
        target.setUsername(source.getUsername());
        target.setAcknowledge(source.isAcknowledge());
        target.setVersion(source.getVersion());
        return target;
    }

    // ------------------------------------------------------------
    // COMMON DO2DTO METHODS
    // ------------------------------------------------------------

    private InternationalStringDto internationalStringDoToDto(InternationalString source) {
        if (source == null) {
            return null;
        }
        InternationalStringDto target = new InternationalStringDto();
        target.getTexts().addAll(localisedStringDoToDto(source.getTexts()));
        return target;
    }

    private Set<LocalisedStringDto> localisedStringDoToDto(Set<LocalisedString> sources) {
        Set<LocalisedStringDto> targets = new HashSet<LocalisedStringDto>();
        for (LocalisedString source : sources) {
            LocalisedStringDto target = new LocalisedStringDto();
            target.setLabel(source.getLabel());
            target.setLocale(source.getLocale());
            target.setIsUnmodifiable(source.getIsUnmodifiable());
            targets.add(target);
        }
        return targets;
    }

    private Date dateDoToDto(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

    private ExternalItemDto externalItemToDto(ExternalItem source) throws MetamacException {
        ExternalItemDto target = externalItemDoToDtoWithoutUrls(source);
        if (target != null) {
            target.setUri(baseDo2DtoMapper.externalItemApiUrlDoToDto(source.getType(), source.getUri()));
            target.setManagementAppUrl(baseDo2DtoMapper.externalItemWebAppUrlDoToDto(source.getType(), source.getManagementAppUrl()));
        }
        return target;
    }

    private ExternalItemDto externalItemDoToDtoWithoutUrls(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ExternalItemDto target = new ExternalItemDto();
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringDoToDto(source.getTitle()));
        return target;
    }
}
