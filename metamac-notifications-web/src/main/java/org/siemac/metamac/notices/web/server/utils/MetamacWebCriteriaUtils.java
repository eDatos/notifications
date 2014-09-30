package org.siemac.metamac.notices.web.server.utils;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginator;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaRestriction;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.criteria.NoticeCriteriaOrderEnum;
import org.siemac.metamac.notices.core.criteria.NoticeCriteriaPropertyEnum;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.utils.SecurityUtils;
import org.siemac.metamac.web.common.server.ServiceContextHolder;

public class MetamacWebCriteriaUtils {

    public static MetamacCriteria build(NoticeWebCriteria noticeWebCriteria) throws MetamacException {
        MetamacCriteria criteria = new MetamacCriteria();
        // Criteria
        MetamacCriteriaConjunctionRestriction restriction = new MetamacCriteriaConjunctionRestriction();
        restriction.getRestrictions().add(buildNoticeCriteriaRestriction(noticeWebCriteria));
        criteria.setRestriction(restriction);

        // Order
        MetamacCriteriaOrder criteriaOrder = new MetamacCriteriaOrder();
        criteriaOrder.setPropertyName(NoticeCriteriaOrderEnum.CREATED_DATE.name());
        criteriaOrder.setType(OrderTypeEnum.DESC);
        criteria.getOrdersBy().add(criteriaOrder);

        // Pagination
        criteria.setPaginator(new MetamacCriteriaPaginator());
        criteria.getPaginator().setFirstResult(noticeWebCriteria.getFirstResult());
        criteria.getPaginator().setMaximumResultSize(noticeWebCriteria.getMaxResults());
        criteria.getPaginator().setCountTotalResults(true);

        return criteria;
    }

    public static MetamacCriteriaRestriction buildNoticeCriteriaRestriction(NoticeWebCriteria criteria) throws MetamacException {

        MetamacPrincipal metamacPrincipal = SecurityUtils.getMetamacPrincipal(ServiceContextHolder.getCurrentServiceContext());
        String username = metamacPrincipal.getUserId();

        // The web application only shows the notices of the current user
        criteria.setReceiverUsername(username);

        MetamacCriteriaConjunctionRestriction conjunctionRestriction = new MetamacCriteriaConjunctionRestriction();

        if (criteria != null) {

            // General criteria

            MetamacCriteriaDisjunctionRestriction noticeCriteriaDisjuction = new MetamacCriteriaDisjunctionRestriction();
            if (StringUtils.isNotBlank(criteria.getCriteria())) {
                noticeCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.SUBJECT.name(), criteria.getCriteria(), OperationType.ILIKE));
                noticeCriteriaDisjuction.getRestrictions().add(
                        new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.SENDING_APPLICATION.name(), criteria.getCriteria(), OperationType.ILIKE));
                noticeCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.SENDING_USER.name(), criteria.getCriteria(), OperationType.ILIKE));
            }
            conjunctionRestriction.getRestrictions().add(noticeCriteriaDisjuction);

            // Specific criteria

            if (StringUtils.isNotBlank(criteria.getSendingApplication())) {
                conjunctionRestriction.getRestrictions().add(
                        new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.SENDING_APPLICATION.name(), criteria.getSendingApplication(), OperationType.EQ));
            }

            if (StringUtils.isNotBlank(criteria.getSendingUser())) {
                conjunctionRestriction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.SENDING_USER.name(), criteria.getSendingUser(), OperationType.ILIKE));
            }

            if (criteria.getAcknowledge() != null) {
                conjunctionRestriction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.ACKNOWLEDGE.name(), criteria.getAcknowledge(), OperationType.EQ));
            }

            if (criteria.getType() != null) {
                conjunctionRestriction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.TYPE.name(), criteria.getType(), OperationType.EQ));
            }

            if (StringUtils.isNotBlank(criteria.getReceiverUsername())) {
                conjunctionRestriction.getRestrictions().add(
                        new MetamacCriteriaPropertyRestriction(NoticeCriteriaPropertyEnum.RECEIVER_USERNAME.name(), criteria.getReceiverUsername(), OperationType.EQ));
            }

            // TODO METAMAC-1984
        }

        return conjunctionRestriction;
    }
}
