package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import org.siemac.metamac.rest.notices.v1_0.domain.Receiver;

public class ReceiverBuilder extends ReceiverBuilderBase<ReceiverBuilder> {

    public static ReceiverBuilder receiver() {
        return new ReceiverBuilder();
    }

    public ReceiverBuilder() {
        super(new Receiver());
    }

    public Receiver build() {
        return getInstance();
    }
}

class ReceiverBuilderBase<GeneratorT extends ReceiverBuilderBase<GeneratorT>> {

    private final Receiver instance;

    protected ReceiverBuilderBase(Receiver aInstance) {
        instance = aInstance;
    }

    protected Receiver getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withUsername(String aValue) {
        instance.setUsername(aValue);

        return (GeneratorT) this;
    }
}
