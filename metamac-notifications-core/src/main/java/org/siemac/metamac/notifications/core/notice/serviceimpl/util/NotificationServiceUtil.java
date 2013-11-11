package org.siemac.metamac.notifications.core.notice.serviceimpl.util;

import static org.siemac.metamac.notifications.core.invocation.utils.RestCriteriaUtils.appendCommaSeparatedQuotedElement;
import static org.siemac.metamac.notifications.core.invocation.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.notifications.core.invocation.utils.RestCriteriaUtils.fieldComparison;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.domain.Receiver;
import org.siemac.metamac.rest.access_control.v1_0.domain.UserCriteriaPropertyRestriction;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;

public class NotificationServiceUtil {

    public static String createQueryForFindUsers(Notification notification) {
        StringBuilder query = new StringBuilder();

        // Add filter: by usernames
        if (notification.getReceivers() != null && !notification.getReceivers().isEmpty()) {
            appendConditionToQuery(query,
                    fieldComparison(UserCriteriaPropertyRestriction.USERNAME, ComparisonOperator.IN, transformReceiversIntoQuotedCommaSeparatedUsernameString(notification.getReceivers())));
        }

        // Add filter: by statistic operation
        if (notification.getStatisticalOperation() != null) {
            appendConditionToQuery(query,
                    fieldComparison(UserCriteriaPropertyRestriction.STATISTICAL_OPERATION_URN, ComparisonOperator.EQ, processExternalItemsUrn(notification.getStatisticalOperation())));
        }

        // Add filter: by application code
        if (!StringUtils.isEmpty(notification.getSendingApplication())) {
            appendConditionToQuery(query, fieldComparison(UserCriteriaPropertyRestriction.APPLICATION_CODE, ComparisonOperator.EQ, notification.getSendingApplication()));
        }

        // Add filter: by roles
        if (!StringUtils.isEmpty(notification.getRole())) {
            appendConditionToQuery(query, fieldComparison(UserCriteriaPropertyRestriction.ROLE_CODE, ComparisonOperator.EQ, notification.getRole()));
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

    public static String transformStringIntoQuotedCommaSeparatedString(String item) {
        StringBuilder result = new StringBuilder();
        appendCommaSeparatedQuotedElement(result, item);
        return result.toString();
    }

    public static String processExternalItemsUrn(ExternalItem externalItem) {
        return externalItem.getUrn();
    }

    public static List<String> processExternalItemsUrns(Collection<ExternalItem> externalItems) {
        List<String> urns = new ArrayList<String>();
        for (ExternalItem item : externalItems) {
            urns.add(processExternalItemsUrn(item));
        }
        return urns;
    }
}
