package org.siemac.metamac.notices.core.notice.repositoryimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.ReceiverProperties;
import org.springframework.stereotype.Repository;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

/**
 * Repository implementation for Receiver
 */
@Repository("receiverRepository")
public class ReceiverRepositoryImpl extends ReceiverRepositoryBase {

    public ReceiverRepositoryImpl() {
    }

    @Override
    public Receiver retrieveReceiver(String noticeUrn, String username) throws MetamacException {
        List<ConditionalCriteria> conditions = criteriaFor(Receiver.class).distinctRoot().build();
        ConditionalCriteria conditionUsername = criteriaFor(Receiver.class).withProperty(ReceiverProperties.username()).eq(username).buildSingle();
        conditions.add(conditionUsername);

        ConditionalCriteria conditionNoticeUrn = criteriaFor(Receiver.class).withProperty(ReceiverProperties.notice().urn()).eq(noticeUrn).buildSingle();
        conditions.add(conditionNoticeUrn);

        List<Receiver> receiver = findByCondition(conditions);

        if (receiver.size() > 1) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one receiver for notice with urn " + noticeUrn + " and receiver " + username);
        } else if (receiver.size() == 0) {
            throw new MetamacException(ServiceExceptionType.RECEIVER_NOT_FOUND, noticeUrn, username);
        }

        return receiver.get(0);

    }
}
