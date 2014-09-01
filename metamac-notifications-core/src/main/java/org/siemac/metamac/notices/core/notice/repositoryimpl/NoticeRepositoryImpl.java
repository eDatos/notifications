package org.siemac.metamac.notices.core.notice.repositoryimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.NoticeProperties;
import org.springframework.stereotype.Repository;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

/**
 * Repository implementation for Notice
 */
@Repository("noticeRepository")
public class NoticeRepositoryImpl extends NoticeRepositoryBase {

    public NoticeRepositoryImpl() {
    }

    @Override
    public Notice retrieveByUrn(String urn) throws MetamacException {

        List<ConditionalCriteria> condition = criteriaFor(Notice.class).withProperty(NoticeProperties.urn()).eq(urn).distinctRoot().build();

        List<Notice> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.NOTICE_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one notice with urn " + urn);
        }

        return result.get(0);
    }

    @Override
    public List<Notice> findByReceiverUsername(String receiverUsername) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(Notice.class).withProperty(NoticeProperties.receivers().username()).eq(receiverUsername).distinctRoot().build();

        return findByCondition(condition);
    }
}
