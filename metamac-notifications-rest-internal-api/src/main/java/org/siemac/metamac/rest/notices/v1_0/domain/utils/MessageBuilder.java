package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import org.siemac.metamac.rest.notifications.v1_0.domain.Message;
import org.siemac.metamac.rest.notifications.v1_0.domain.ResourcesInternal;

public class MessageBuilder extends MessageBuilderBase<MessageBuilder> {

    public static MessageBuilder message() {
        return new MessageBuilder();
    }

    public MessageBuilder() {
        super(new Message());
    }

    public Message build() {
        return getInstance();
    }
}

class MessageBuilderBase<GeneratorT extends MessageBuilderBase<GeneratorT>> {

    private final Message instance;

    protected MessageBuilderBase(Message aInstance) {
        instance = aInstance;
    }

    protected Message getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withText(String aValue) {
        instance.setText(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withResources(ResourcesInternal aValue) {
        instance.setResources(aValue);

        return (GeneratorT) this;
    }
}
