package org.siemac.metamac.notifications.core.notice.serviceimpl.util;

import static org.siemac.metamac.notifications.core.invocation.utils.RestCriteriaUtils.appendCommaSeparatedQuotedElement;
import static org.siemac.metamac.notifications.core.invocation.utils.RestCriteriaUtils.appendConditionDisjuctionToQuery;
import static org.siemac.metamac.notifications.core.invocation.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.notifications.core.invocation.utils.RestCriteriaUtils.fieldComparison;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.notice.domain.App;
import org.siemac.metamac.notifications.core.notice.domain.Notification;
import org.siemac.metamac.notifications.core.notice.domain.Receiver;
import org.siemac.metamac.notifications.core.notice.domain.Role;
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

        // Add filter: by statistic operations
        if (notification.getStatisticalOperations() != null && !notification.getStatisticalOperations().isEmpty()) {
            appendConditionDisjuctionToQuery(query,
                    fieldComparison(UserCriteriaPropertyRestriction.STATISTICAL_OPERATION_URN, ComparisonOperator.EQ, processExternalItemsUrns(notification.getStatisticalOperations())),
                    fieldComparison(UserCriteriaPropertyRestriction.STATISTICAL_OPERATION_URN, ComparisonOperator.IS_NULL, null));
        }

        // Add filter: by applications code
        if (notification.getApps() != null && !notification.getApps().isEmpty()) {
            appendConditionToQuery(query,
                    fieldComparison(UserCriteriaPropertyRestriction.APPLICATION_CODE, ComparisonOperator.IN, transformAppsIntoQuotedCommaSeparatedUsernameString(notification.getApps())));
        }

        // Add filter: by roles
        if (notification.getRoles() != null && !notification.getRoles().isEmpty()) {
            appendConditionToQuery(query,
                    fieldComparison(UserCriteriaPropertyRestriction.ROLE_CODE, ComparisonOperator.IN, transformRolesIntoQuotedCommaSeparatedUsernameString(notification.getRoles())));
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

    public static List<String> processExternalItemsUrns(Collection<ExternalItem> externalItems) {
        List<String> urns = new ArrayList<String>();
        for (ExternalItem item : externalItems) {
            urns.add(processExternalItemsUrn(item));
        }
        return urns;
    }
}
