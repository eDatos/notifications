package org.siemac.metamac.notices.core.mapper;

import java.util.Date;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.siemac.metamac.notices.core.dto.MessageDto;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.dto.ReceiverDto;
import org.siemac.metamac.notices.core.error.ServiceExceptionParameters;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.core.notice.domain.App;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeRepository;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;
import org.siemac.metamac.notices.core.notice.exception.NoticeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("noticeDto2DoMapper")
public class NoticeDto2DoMapperImpl implements NoticeDto2DoMapper {

    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public Notice noticeDtoToDo(NoticeDto source) throws MetamacException {

        if (source == null) {
            return null;
        }

        Notice target = null;
        if (source.getId() == null) {
            target = new Notice(source.getSendingApplication(), source.getSubject(), source.getType());
        } else {
            try {
                target = noticeRepository.findById(source.getId());
                OptimisticLockingUtils.checkVersion(target.getVersion(), source.getVersion());
            } catch (NoticeNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.NOTICE_NOT_FOUND).withMessageParameters(ServiceExceptionParameters.NOTICE)
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }

        noticeDto2Do(source, target);

        return target;
    }

    private void noticeDto2Do(NoticeDto source, Notice target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.NOTICE).build();
        }

        target.setSubject(source.getSubject());
        target.setNoticeType(source.getType());
        target.setExpirationDate(dateDtoToDo(source.getExpirationDate()));
        target.setSendingApplication(source.getSendingApplication());
        target.setSendingUser(source.getSendingUser());
        messagesDto2Do(source, target);
        receiversDto2Do(source, target);
        rolesDto2Do(source, target);
        statisticalOperationsDto2Dto(source, target);
        applicationsDto2Dto(source, target);
    }

    private void messagesDto2Do(NoticeDto source, Notice target) {
        target.removeAllMessages();
        for (MessageDto sourceMessageDto : source.getMessages()) {
            Message message = new Message(sourceMessageDto.getText());
            target.addMessage(message);
        }
    }

    private void receiversDto2Do(NoticeDto source, Notice target) {
        target.removeAllReceivers();
        for (ReceiverDto receiverDto : source.getReceivers()) {
            Receiver receiver = new Receiver();
            receiver.setAcknowledge(receiverDto.isAcknowledge());
            receiver.setUsername(receiverDto.getUsername());
            target.addReceiver(receiver);
        }
    }

    private void rolesDto2Do(NoticeDto source, Notice target) {
        target.removeAllRoles();
        for (String roleName : source.getRoles()) {
            Role role = new Role();
            role.setName(roleName);
            target.addRole(role);
        }
    }

    private void statisticalOperationsDto2Dto(NoticeDto source, Notice target) {
        target.removeAllStatisticalOperations();
        for (String code : source.getStatisticalOperationCodes()) {
            StatisticalOperation operation = new StatisticalOperation();
            operation.setName(code);
            target.addStatisticalOperation(operation);
        }
    }

    private void applicationsDto2Dto(NoticeDto source, Notice target) {
        target.removeAllApps();
        for (String appName : source.getApplications()) {
            App app = new App();
            app.setName(appName);
            target.addApp(app);
        }
    }

    // ------------------------------------------------------------
    // COMMON DTO2DO METHODS
    // ------------------------------------------------------------

    public DateTime dateDtoToDo(Date source) {
        if (source == null) {
            return null;
        }
        return new DateTime(source);
    }
}
