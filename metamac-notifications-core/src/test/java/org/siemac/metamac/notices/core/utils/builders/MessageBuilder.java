package org.siemac.metamac.notices.core.utils.builders;

import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.notice.domain.Message;
import org.siemac.metamac.notices.core.utils.mocks.templates.NoticesDoMocks;

public class MessageBuilder extends MessageBuilderBase<MessageBuilder> {

    // By default we generate mock values for required-in-creation fields
    // This lets a simple interface for the builder
    public static MessageBuilder message() {
        String text = NoticesDoMocks.mockSentence(10);
        return new MessageBuilder(text);
    }

    public MessageBuilder(String text) {
        super(new Message(text));
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
    public GeneratorT withAddedResource(ExternalItem externalItem) {
        instance.getResources().add(externalItem);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withResources(ExternalItem... aValue) {
        instance.getResources().clear();
        for (ExternalItem resource : aValue) {
            instance.getResources().add(resource);
        }
        return (GeneratorT) this;
    }
}
