package org.siemac.metamac.rest.notifications.v1_0.domain.utils;

import java.math.BigInteger;

import org.siemac.metamac.rest.notifications.v1_0.domain.StatisticalOperations;

public class StatisticalOperationsUtils {

    public static StatisticalOperations createStatisticalOperationsList(String... statisticalOperationsUrns) {
        StatisticalOperations statisticalOperations = new StatisticalOperations();
        for (String urn : statisticalOperationsUrns) {
            statisticalOperations.getStatisticalOperations().add(StatisticalOperationBuilder.statisticalOperation().withName(urn.toString()).build());
        }

        statisticalOperations.setTotal(new BigInteger(String.valueOf(statisticalOperations.getStatisticalOperations().size())));
        return statisticalOperations;
    }
}
