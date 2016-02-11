package org.siemac.metamac.notices.core.notice.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.notices.core.channel.mail.serviceimpl.MailChannelService;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.notices.core.constants.NoticesMessagesConstants;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.core.invocation.service.AccessControlRestInternalFacade;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.core.notice.exception.NoticeNotFoundException;
import org.siemac.metamac.notices.core.notice.serviceapi.NoticesService;
import org.siemac.metamac.notices.core.notice.serviceapi.validators.NoticesServiceInvocationValidator;
import org.siemac.metamac.notices.core.notice.serviceimpl.util.NoticesServiceUtil;
import org.siemac.metamac.rest.access_control.v1_0.domain.App;
import org.siemac.metamac.rest.access_control.v1_0.domain.Apps;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of NoticeService.
 */
@Service(NoticesService.BEAN_ID)
public class NoticesServiceImpl extends NoticesServiceImplBase {

    private final static Logger               logger = LoggerFactory.getLogger(NoticesServiceImpl.class);

    @Autowired
    private NoticesServiceInvocationValidator noticeServiceInvocationValidator;

    @Autowired
    private MailChannelService                mailChannelService;

    @Autowired
    private AccessControlRestInternalFacade   accessControlRestInternalFacade;

    @Autowired
    private NoticesConfigurationService       noticesConfigurationService;

    public NoticesServiceImpl() {
    }

    @Override
    public Notice findNoticeById(ServiceContext ctx, Long id) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkFindNoticeById(ctx, id);

        try {
            return getNoticeRepository().findById(id);
        } catch (NoticeNotFoundException e) {
            logger.error(ServiceExceptionType.NOTICE_NOT_FOUND.getCode(), e);
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

        fillNoticeMetadata(ctx, notice);

        // Calculate receivers
        List<User> users = calculateReceiversOfAccessControl(notice);
        addReceiversToNotice(users, notice);

        notice = getNoticeRepository().save(notice);

        // Send notice
        String[] mailsTo = extractMailsTo(notice);
        String replyTo = extractReplyTo(notice);

        mailChannelService.sendMail(ctx, notice, mailsTo, replyTo);

        return notice;
    }

    @Override
    public Notice updateNotice(ServiceContext ctx, Notice notice) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkUpdateNotice(ctx, notice);

        return getNoticeRepository().save(notice);
    }

    @Override
    public void markNoticeForReceiverAsRead(ServiceContext ctx, String noticeUrn, String username) throws MetamacException {
        // Validations
        noticeServiceInvocationValidator.checkMarkNoticeForReceiverAsRead(ctx, noticeUrn, username);

        // Retrieve receiver
        Receiver receiver = retrieveReceiver(ctx, noticeUrn, username);

        receiver.setAcknowledge(Boolean.TRUE);
        updateReceiver(ctx, receiver);
    }

    @Override
    public void markNoticeForReceiverAsUnread(ServiceContext ctx, String noticeUrn, String username) throws MetamacException {
        // Validations
        noticeServiceInvocationValidator.checkMarkNoticeForReceiverAsUnread(ctx, noticeUrn, username);

        // Retrieve receiver
        Receiver receiver = retrieveReceiver(ctx, noticeUrn, username);

        receiver.setAcknowledge(Boolean.FALSE);
        updateReceiver(ctx, receiver);
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
    public PagedResult<Notice> findNoticeByCondition(ServiceContext ctx, List<ConditionalCriteria> condition, PagingParameter pagingParameter) throws MetamacException {

        // Validations
        noticeServiceInvocationValidator.checkFindNoticeByCondition(ctx, condition, pagingParameter);

        initCriteriaConditions(condition, Notice.class);

        return getNoticeRepository().findByCondition(condition, pagingParameter);
    }

    @Override
    public List<Notice> findUserNotices(ServiceContext ctx, String receiverUsername) throws MetamacException {
        // Validations
        noticeServiceInvocationValidator.checkFindUserNotices(ctx, receiverUsername);

        return getNoticeRepository().findByReceiverUsername(receiverUsername);
    }

    @Override
    public Receiver retrieveReceiver(ServiceContext ctx, String noticeUrn, String username) throws MetamacException {
        // Validations
        noticeServiceInvocationValidator.checkRetrieveReceiver(ctx, noticeUrn, username);

        return getReceiverRepository().retrieveReceiver(noticeUrn, username);
    }

    @Override
    public Receiver updateReceiver(ServiceContext ctx, Receiver receiver) throws MetamacException {
        // Validations
        noticeServiceInvocationValidator.checkUpdateReceiver(ctx, receiver);

        return getReceiverRepository().save(receiver);
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

    private Notice addReceiversToNotice(List<User> users, Notice notice) throws MetamacException {
        List<Receiver> receivers = new ArrayList<Receiver>();

        for (User user : users) {
            Receiver receiver = new Receiver();
            receiver.setNotice(notice);
            receiver.setUsername(user.getUsername());
            receiver.setAcknowledge(Boolean.FALSE);
            receivers.add(receiver);
        }

        notice.getReceivers().clear();
        notice.getReceivers().addAll(receivers);

        return notice;
    }

    private List<User> calculateReceiversOfAccessControl(Notice notice) throws MetamacException {
        String queryForFindUsers = NoticesServiceUtil.createQueryForFindNoticeReceivers(notice);

        if (StringUtils.isEmpty(queryForFindUsers)) {
            // If the user does not specify any user (receivers or conditions), the notice is for all users that exists in access-control database
            queryForFindUsers = null;
        }

        List<User> users = accessControlRestInternalFacade.findUsers(queryForFindUsers);
        checkUsersValidity(notice, users, queryForFindUsers);

        return users;
    }

    private void checkUsersValidity(Notice notice, List<User> users, String queryForFindUsers) throws MetamacException {
        if (users.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.NOTICE_RECEIVERS_NOT_FOUND, queryForFindUsers);
        }

        Set<String> receiverUserNames = NoticesServiceUtil.getReceiversUsernames(notice);
        for (String receiverUsername : receiverUserNames) {
            if (!containsUserWithUsername(users, receiverUsername)) {
                throw new MetamacException(ServiceExceptionType.RECEIVER_USERNAME_NOT_FOUND, receiverUsername);
            }
        }
    }

    private boolean containsUserWithUsername(List<User> users, String username) {
        for (User user : users) {
            if (StringUtils.equals(user.getUsername(), username)) {
                return true;
            }
        }
        return false;
    }

    private List<User> calculateReceiversOfMail(Notice notice) throws MetamacException {
        String queryForFindUsers = NoticesServiceUtil.createQueryForFindNoticeReveiversThatReceiveMail(notice);

        if (StringUtils.isEmpty(queryForFindUsers)) {
            // If the user does not specify any user (receivers or conditions), the notice is for all users that exists in access-control database
            queryForFindUsers = null;
        }

        List<User> users = accessControlRestInternalFacade.findUsers(queryForFindUsers);

        if (users.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.NOTICE_RECEIVERS_NOT_FOUND, queryForFindUsers);
        }

        return users;
    }

    private String[] extractMailsTo(Notice notice) throws MetamacException {
        List<User> users = calculateReceiversOfMail(notice);
        List<String> mailsTo = new ArrayList<String>();
        
        for (User user: users) {
            if (!mailsTo.contains(user.getMail())) {
                mailsTo.add(user.getMail());
            }
        }
        
        return mailsTo.toArray(new String[mailsTo.size()]);
    }

    private String extractReplyTo(Notice notice) throws MetamacException {
        if (notice.getSendingUser() == null) {
            return null;
        }
        
        String queryForFindUsers = NoticesServiceUtil.createQueryForFindUser(notice.getSendingUser());

        if (StringUtils.isEmpty(queryForFindUsers)) {
            throw new RuntimeException("Unexpected error: Impossible to create query for access-control api");
        }

        List<User> users = accessControlRestInternalFacade.findUsers(queryForFindUsers);

        if (users.isEmpty()) {
            throw new MetamacException(ServiceExceptionType.NOTICE_REPLYTO_NOT_FOUND, queryForFindUsers);
        }

        return users.iterator().next().getMail();
    }

    private String getSendingApplicationName(ServiceContext serviceContext, Notice notice) throws MetamacException {
        Apps apps = accessControlRestInternalFacade.retrieveApps(serviceContext);
        for (App app : apps.getApps()) {
            if (notice.getSendingApplication().equals(app.getCode())) {
                return app.getTitle().toUpperCase();
            }
        }
        return notice.getSendingApplication();
    }

    private void fillNoticeMetadata(ServiceContext serviceContext, Notice notice) throws MetamacException {
        // Generate URN
        notice.setUrn(GeneratorUrnUtils.generateSiemacNoticeUrn(java.util.UUID.randomUUID().toString()));

        // Add prefix to subject
        String prefix = null;
        if (NoticeType.NOTIFICATION.equals(notice.getNoticeType())) {
            prefix = getSendingApplicationName(serviceContext, notice);
        } else {
            prefix = LocaleUtil.getMessageForCode(NoticesMessagesConstants.NOTICE_TYPE_ANNOUNCEMENT, noticesConfigurationService.retrieveLanguageDefaultLocale()).toUpperCase();
        }
        notice.setSubject("[" + prefix + "] " + notice.getSubject());
    }
}
