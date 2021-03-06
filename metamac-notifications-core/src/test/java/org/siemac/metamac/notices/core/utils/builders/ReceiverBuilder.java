package org.siemac.metamac.notices.core.utils.builders;

import org.siemac.metamac.notices.core.notice.domain.Receiver;

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

    @SuppressWarnings("unchecked")
    public GeneratorT withAcknowledge(Boolean aValue) {
        instance.setAcknowledge(aValue);
        return (GeneratorT) this;
    }
}
