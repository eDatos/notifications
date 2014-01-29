package org.siemac.metamac.notices.core.notice.serviceimpl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.notices.core.channel.mail.serviceimpl.MailChannelService;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.core.invocation.service.AccessControlRestInternalFacade;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.exception.NoticeNotFoundException;
import org.siemac.metamac.notices.core.notice.serviceapi.validators.NoticesServiceInvocationValidator;
import org.siemac.metamac.notices.core.notice.serviceimpl.util.NoticesServiceUtil;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of NoticeService.
 */
@Service("noticeService")
public class NoticesServiceImpl extends NoticesServiceImplBase {

    @Autowired
    private NoticesServiceInvocationValidator noticeServiceInvocationValidator;

    @Autowired
    private MailChannelService                mailChannelService;

    @Autowired
    private AccessControlRestInternalFacade   accessControlRestInternalFacade;

    public NoticesServiceImpl() {
    }

    @Override
    public Notice findNoticeById(ServiceContext ctx, Long id) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkFindNoticeById(ctx, id);

        try {
            return getNoticeRepository().findById(id);
        } catch (NoticeNotFoundException e) {
            throw new MetamacException(ServiceExceptionType.NOTICE_NOT_FOUND, id);
        }
    }

    @Override
    public Notice retrieveNoticeByUrn(ServiceContext ctx, String urn) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkRetrieveNoticeByUrn(ctx, urn);

        return getNoticeRepository().retrieveByUrn(urn);
    }

    @Override
    public Notice createNotice(ServiceContext ctx, Notice notice) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkCreateNotice(ctx, notice);

        // Generate URN
        // FIXME: change el random por un get del uuid - añadir a la entity
        notice.setUrn(GeneratorUrnUtils.generateSiemacNoticeUrn(java.util.UUID.randomUUID().toString()));

        notice = getNoticeRepository().save(notice);

        // Send notice
        mailChannelService.sendMail(ctx, notice, extractMailsTo(notice), extractReplyTo(notice));

        return notice;
    }

    @Override
    public Notice updateNotice(ServiceContext ctx, Notice notice) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkUpdateNotice(ctx, notice);

        return getNoticeRepository().save(notice);
    }

    @Override
    public void deleteNotice(ServiceContext ctx, Long id) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkDeleteNotice(ctx, id);

        Notice notice = findNoticeById(ctx, id);
        getNoticeRepository().delete(notice);
    }

    @Override
    public List<Notice> findAllNotice(ServiceContext ctx) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkFindAllNotice(ctx);

        return getNoticeRepository().findAll();
    }

    @Override
    public List<Notice> findNoticeByCondition(ServiceContext ctx, List<ConditionalCriteria> condition) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkFindNoticeByCondition(ctx, condition);

        initCriteriaConditions(condition, Notice.class);

        return getNoticeRepository().findByCondition(condition);
    }

    // ----------------------------------------------------------------------
    // UTILS
    // ----------------------------------------------------------------------
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<ConditionalCriteria> initCriteriaConditions(List<ConditionalCriteria> conditions, Class entityClass) {
        List<ConditionalCriteria> conditionsEntity = ConditionalCriteriaBuilder.criteriaFor(entityClass).build();
        if (conditions != null) {
            conditionsEntity.addAll(conditions);
        }
        return conditionsEntity;
    }

    private String[] extractMailsTo(Notice notice) throws MetamacException {
        String queryForFindUsers = NoticesServiceUtil.createQueryForFindUsers(notice);

        if (StringUtils.isEmpty(queryForFindUsers)) {
            throw new RuntimeException("Unexpected error: Impossible to create query for acces-control api");
        }

        List<User> users = accessControlRestInternalFacade.findUsers(queryForFindUsers);

        if (users.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.NOTICE_RECEIVERS_NOT_FOUND);
        }

        String[] mailsTo = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            mailsTo[i] = users.get(i).getMail();
        }
        return mailsTo;
    }

    private String extractReplyTo(Notice notice) throws MetamacException {
        String queryForFindUsers = NoticesServiceUtil.createQueryForFindUser(notice.getSendingUser());

        if (StringUtils.isEmpty(queryForFindUsers)) {
            throw new RuntimeException("Unexpected error: Impossible to create query for access-control api");
        }

        List<User> users = accessControlRestInternalFacade.findUsers(queryForFindUsers);

        if (users.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.NOTICE_RECEIVERS_NOT_FOUND);
        }

        return users.iterator().next().getMail();
    }
}