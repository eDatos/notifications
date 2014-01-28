package org.siemac.metamac.notices.core.utils.builders;

import org.siemac.metamac.notices.core.notice.domain.StatisticalOperation;

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
    public GeneratorT withName(String aValue) {
        instance.setName(aValue);

        return (GeneratorT) this;
    }
}
