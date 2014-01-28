package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import org.siemac.metamac.rest.notices.v1_0.domain.StatisticalOperation;

public class StatisticalOperationBuilder extends StatisticalOperationBuilderBase<StatisticalOperationBuilder> {

    public static StatisticalOperationBuilder statisticalOperation() {
        return new StatisticalOperationBuilder();
    }

    public StatisticalOperationBuilder() {
        super(new StatisticalOperation());
    }

    public StatisticalOperation build() {
        return getInstance();
    }
}

class StatisticalOperationBuilderBase<GeneratorT extends StatisticalOperationBuilderBase<GeneratorT>> {

    private final StatisticalOperation instance;

    protected StatisticalOperationBuilderBase(StatisticalOperation aInstance) {
        instance = aInstance;
    }

    protected StatisticalOperation getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withUrn(String aValue) {
        instance.setUrn(aValue);

        return (GeneratorT) this;
    }
}
