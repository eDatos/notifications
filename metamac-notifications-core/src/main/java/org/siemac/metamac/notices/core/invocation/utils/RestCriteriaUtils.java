package org.siemac.metamac.notices.core.invocation.utils;

import static org.siemac.metamac.rest.api.constants.RestApiConstants.BLANK;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.COMMA;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.LEFT_PARENTHESIS;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.QUOTE;
import static org.siemac.metamac.rest.api.constants.RestApiConstants.RIGHT_PARENTHESIS;

import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;

public class RestCriteriaUtils {

    @SuppressWarnings("rawtypes")
    public static String fieldComparison(Enum field, ComparisonOperator operator, Object value) {
        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append(field).append(BLANK).append(operator.name()).append(BLANK);
        if (ComparisonOperator.IN.equals(operator)) {
            conditionBuilder.append(LEFT_PARENTHESIS).append(value).append(RIGHT_PARENTHESIS);
        } else if (!ComparisonOperator.IS_NULL.equals(operator) && !ComparisonOperator.IS_NOT_NULL.equals(operator)) {
            conditionBuilder.append(QUOTE).append(value).append(QUOTE);
        }
        return conditionBuilder.toString();
    }

    public static void appendConditionToQuery(StringBuilder queryBuilder, String condition) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(BLANK).append(LogicalOperator.AND.name()).append(BLANK);
        }
        queryBuilder.append(condition);
    }

    public static void appendConditionToQuery(StringBuilder queryBuilder, LogicalOperator operator, String condition) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(BLANK).append(operator.name()).append(BLANK);
        }
        queryBuilder.append(condition);
    }

    public static void appendConditionDisjuctionToQuery(StringBuilder queryBuilder, String condition1, String condition2) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(BLANK).append(LogicalOperator.AND.name()).append(BLANK);
        }
        queryBuilder.append(LEFT_PARENTHESIS).append(condition1).append(BLANK).append(LogicalOperator.OR.name()).append(BLANK).append(condition2).append(RIGHT_PARENTHESIS);
    }

    public static void appendCommaSeparatedQuotedElement(StringBuilder stringBuilder, String element) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(COMMA);
        }
        stringBuilder.append(QUOTE).append(element).append(QUOTE);
    }
}
