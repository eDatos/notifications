package org.siemac.metamac.notices.core.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.common.domain.InternationalString;
import org.siemac.metamac.notices.core.common.domain.LocalisedString;
import org.siemac.metamac.notices.core.dto.MessageDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.springframework.stereotype.Component;

@Component("noticeDo2DtoMapper")
public class NoticeDo2DtoMapperImpl implements NoticeDo2DtoMapper {

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

        for (Message message : source.getMessages()) {
            target.addMessage(messageDoToDto(message));
        }
    }

    private MessageDto messageDoToDto(Message source) {
        MessageDto target = new MessageDto();
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        target.setText(source.getText());

        for (ExternalItem externalItem : source.getResources()) {
            target.addResource(externalItemDoToDto(externalItem));
        }

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

    private ExternalItemDto externalItemDoToDto(ExternalItem source) {
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
