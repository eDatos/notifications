package org.siemac.metamac.notices.core.notice.serviceimpl.util;

import static org.siemac.metamac.notices.core.invocation.utils.RestCriteriaUtils.appendCommaSeparatedQuotedElement;
import static org.siemac.metamac.notices.core.invocation.utils.RestCriteriaUtils.appendConditionDisjuctionToQuery;
import static org.siemac.metamac.notices.core.invocation.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.notices.core.invocation.utils.RestCriteriaUtils.fieldComparison;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.notice.domain.App;
import org.siemac.metamac.notices.core.notice.domain.Notice;
import org.siemac.metamac.notices.core.notice.domain.Receiver;
import org.siemac.metamac.notices.core.notice.domain.Role;
import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;
import org.siemac.metamac.rest.access_control.v1_0.domain.UserCriteriaPropertyRestriction;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;

public class NoticesServiceUtil {

    public static String createQueryForFindNoticeReveiversThatReceiveMail(Notice notice) {
        StringBuilder query = new StringBuilder(createQueryForFindNoticeReceivers(notice));

        // Add filter: send mail = true
        appendConditionToQuery(query, fieldComparison(UserCriteriaPropertyRestriction.SEND_MAIL, ComparisonOperator.EQ, Boolean.TRUE));

        return query.toString();

    }

    public static String createQueryForFindNoticeReceivers(Notice notice) {
        StringBuilder query = new StringBuilder();

        // Add filter: by usernames
        if (notice.getReceivers() != null && !notice.getReceivers().isEmpty()) {
            appendConditionToQuery(query,
                    fieldComparison(UserCriteriaPropertyRestriction.USERNAME, ComparisonOperator.IN, transformReceiversIntoQuotedCommaSeparatedUsernameString(notice.getReceivers())));
        }

        // Add filter: by statistic operations
        if (notice.getStatisticalOperations() != null && !notice.getStatisticalOperations().isEmpty()) {
            appendConditionDisjuctionToQuery(
                    query,
                    fieldComparison(UserCriteriaPropertyRestriction.STATISTICAL_OPERATION_URN, ComparisonOperator.IN,
                            transformStatisticalOperationsIntoCommaSeparatedUrns(notice.getStatisticalOperations())),
                    fieldComparison(UserCriteriaPropertyRestriction.STATISTICAL_OPERATION_URN, ComparisonOperator.IS_NULL, null));
        }

        // Add filter: by applications code
        if (notice.getApps() != null && !notice.getApps().isEmpty()) {
            appendConditionToQuery(query,
                    fieldComparison(UserCriteriaPropertyRestriction.APPLICATION_CODE, ComparisonOperator.IN, transformAppsIntoQuotedCommaSeparatedUsernameString(notice.getApps())));
        }

        // Add filter: by roles
        if (notice.getRoles() != null && !notice.getRoles().isEmpty()) {
            appendConditionToQuery(query, fieldComparison(UserCriteriaPropertyRestriction.ROLE_CODE, ComparisonOperator.IN, transformRolesIntoQuotedCommaSeparatedUsernameString(notice.getRoles())));
        }

        return query.toString();
    }

    public static String createQueryForFindUser(String username) {
        StringBuilder query = new StringBuilder();

        // Add filter: by username
        if (!StringUtils.isEmpty(username)) {
            appendConditionToQuery(query, fieldComparison(UserCriteriaPropertyRestriction.USERNAME, ComparisonOperator.IN, transformStringIntoQuotedCommaSeparatedString(username)));
        }
        return query.toString();
    }

    public static String transformReceiversIntoQuotedCommaSeparatedUsernameString(List<Receiver> receivers) {
        StringBuilder result = new StringBuilder();
        for (Receiver receiver : receivers) {
            appendCommaSeparatedQuotedElement(result, receiver.getUsername());
        }
        return result.toString();
    }

    public static String transformRolesIntoQuotedCommaSeparatedUsernameString(List<Role> roles) {
        StringBuilder result = new StringBuilder();
        for (Role role : roles) {
            appendCommaSeparatedQuotedElement(result, role.getName());
        }
        return result.toString();
    }

    public static String transformAppsIntoQuotedCommaSeparatedUsernameString(List<App> apps) {
        StringBuilder result = new StringBuilder();
        for (App app : apps) {
            appendCommaSeparatedQuotedElement(result, app.getName());
        }
        return result.toString();
    }

    public static String transformStringIntoQuotedCommaSeparatedString(String item) {
        StringBuilder result = new StringBuilder();
        appendCommaSeparatedQuotedElement(result, item);
        return result.toString();
    }

    public static String processExternalItemsUrn(ExternalItem externalItem) {
        return externalItem.getUrn();
    }

    public static String processStatisticalOperationUrn(StatisticalOperation statisticalOperation) {
        return statisticalOperation.getName();
    }

    public static String transformStatisticalOperationsIntoCommaSeparatedUrns(List<StatisticalOperation> statisticalOperations) {
        StringBuilder result = new StringBuilder();
        for (StatisticalOperation statisticalOperation : statisticalOperations) {
            appendCommaSeparatedQuotedElement(result, processStatisticalOperationUrn(statisticalOperation));
        }
        return result.toString();
    }
}
