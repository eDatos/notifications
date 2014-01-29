package org.siemac.metamac.notices.core.notice.serviceimpl.validators;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.notices.core.error.ServiceExceptionParameters;
import org.siemac.metamac.notices.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticeType;
import org.siemac.metamac.notices.core.utils.NoticesValidationUtils;

public class NoticesServiceInvocationValidatorImpl {

    // ------------------------------------------------------------------------------------
    // NOTICES
    // ------------------------------------------------------------------------------------

    public static void checkFindNoticeById(Long id, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(id, ServiceExceptionSingleParameters.ID, exceptions);
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

    public static void checkFindNoticeByCondition(List<ConditionalCriteria> condition, List<MetamacExceptionItem> exceptions) {
        // Nothing to check
    }

    public static void checkRetrieveNoticeByUrn(String urn, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkParameterRequired(urn, ServiceExceptionParameters.URN, exceptions);
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

        if (notice.isMark()) {
            // TODO lanzar exepcion de modificar un aviso que ya está marcado
            // TODO EN realidad este campo no se puede mapear en este servicio. Se modifica en un servicio diferente
            throw new RuntimeException("The notice is unmodifiable, is already marked.");
        }

        checkNoticeMetadata(notice, exceptions);
    }

    private static void checkNoticeMetadata(Notice notice, List<MetamacExceptionItem> exceptions) {
        NoticesValidationUtils.checkMetadataRequired(notice.getSendingUser(), ServiceExceptionParameters.NOTICE__SENDING_USER, exceptions);
        NoticesValidationUtils.checkMetadataRequired(notice.getNoticeType(), ServiceExceptionParameters.NOTICE__NOTICE_TYPE, exceptions);
        NoticesValidationUtils.checkMetadataRequired(notice.getSubject(), ServiceExceptionParameters.NOTICE__SUBJECT, exceptions);
        NoticesValidationUtils.checkMetadataRequired(notice.getMessages(), ServiceExceptionParameters.NOTICE__MESSAGES, exceptions);

        // when noticeType is "ADVERTISEMENT"
        if (NoticeType.ADVERTISEMENT.equals(notice.getNoticeType())) {
            NoticesValidationUtils.checkMetadataRequired(notice.getExpirationDate(), ServiceExceptionParameters.NOTICE__EXPIRATION_DATE, exceptions);
        }

    }

}
