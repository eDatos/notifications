package org.siemac.metamac.notices.core.notice.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.notices.core.error.ServiceExceptionBaseParameters;
import org.siemac.metamac.notices.core.error.ServiceExceptionParameters;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.utils.NoticesValidationUtils;

public class NoticesServiceInvocationValidatorImpl {

    private NoticesServiceInvocationValidatorImpl() {
    }

    // ------------------------------------------------------------------------------------
    // NOTICES
    // ------------------------------------------------------------------------------------

    public static void checkFindNoticeById(Long id, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(id, ServiceExceptionBaseParameters.ID, exceptions);
    }

    public static void checkCreateNotice(Notice notice, List<MetamacExceptionItem> exceptions) {
        checkNewNotice(notice, exceptions);
    }

    public static void checkUpdateNotice(Notice notice, List<MetamacExceptionItem> exceptions) {
        checkExistingNotice(notice, exceptions);
    }

    public static void checkDeleteNotice(Long id, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(id, ServiceExceptionParameters.ID, exceptions);
    }

    public static void checkFindAllNotice(List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkFindNoticeByCondition(List<ConditionalCriteria> condition, PagingParameter pagingParameter, List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkRetrieveNoticeByUrn(String urn, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
    }

    public static void checkFindUserNotices(String username, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(username, ServiceExceptionParameters.RECEIVER__USERNAME, exceptions);
    }

    public static void checkMarkNoticeForReceiverAsRead(String noticeUrn, String username, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(username, ServiceExceptionParameters.RECEIVER__USERNAME, exceptions);
        NoticesValidationUtils.checkParameterRequired(noticeUrn, ServiceExceptionParameters.NOTICE__URN, exceptions);
    }

    public static void checkMarkNoticeForReceiverAsUnread(String noticeUrn, String username, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(username, ServiceExceptionParameters.RECEIVER__USERNAME, exceptions);
        NoticesValidationUtils.checkParameterRequired(noticeUrn, ServiceExceptionParameters.NOTICE__URN, exceptions);
    }

    public static void checkUpdateReceiver(Receiver receiver, List<MetamacExceptionItem> exceptions) {
        checkExistingReceiver(receiver, exceptions);
    }

    public static void checkRetrieveReceiver(String noticeUrn, String username, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(username, ServiceExceptionParameters.RECEIVER__USERNAME, exceptions);
        NoticesValidationUtils.checkParameterRequired(noticeUrn, ServiceExceptionParameters.NOTICE__URN, exceptions);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    // NOTICES
    // --------------

    private static void checkNewNotice(Notice notice, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(notice, ServiceExceptionParameters.NOTICE, exceptions);

        if (notice == null) {
            return;
        }

        checkNoticeMetadata(notice, exceptions);

        // Metadata that must be empty for new entities
        NoticesValidationUtils.checkMetadataEmpty(notice.getId(), ServiceExceptionParameters.NOTICE__ID, exceptions);
        NoticesValidationUtils.checkMetadataEmpty(notice.getVersion(), ServiceExceptionParameters.NOTICE__VERSION, exceptions);
        NoticesValidationUtils.checkMetadataEmpty(notice.getUrn(), ServiceExceptionParameters.NOTICE__URN, exceptions);
    }

    private static void checkExistingNotice(Notice notice, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(notice, ServiceExceptionParameters.NOTICE, exceptions);

        if (notice == null) {
            return;
        }

        checkNoticeMetadata(notice, exceptions);
    }

    private static void checkNoticeMetadata(Notice notice, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkMetadataRequired(notice.getNoticeType(), ServiceExceptionParameters.NOTICE__NOTICE_TYPE, exceptions);
        NoticesValidationUtils.checkMetadataRequired(notice.getSubject(), ServiceExceptionParameters.NOTICE__SUBJECT, exceptions);
        NoticesValidationUtils.checkMetadataRequired(notice.getMessages(), ServiceExceptionParameters.NOTICE__MESSAGES, exceptions);
    }

    private static void checkExistingReceiver(Receiver receiver, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(receiver, ServiceExceptionParameters.RECEIVER, exceptions);

        if (receiver == null) {
            return;
        }

        checkReceiverMetadata(receiver, exceptions);

    }

    private static void checkReceiverMetadata(Receiver receiver, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkMetadataRequired(receiver.getNotice(), ServiceExceptionParameters.RECEIVER__NOTICE, exceptions);
        NoticesValidationUtils.checkMetadataRequired(receiver.getUsername(), ServiceExceptionParameters.RECEIVER__USERNAME, exceptions);
    }

}
