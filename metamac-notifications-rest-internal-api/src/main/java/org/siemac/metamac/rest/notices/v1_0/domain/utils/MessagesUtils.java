package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import java.math.BigInteger;

import org.siemac.metamac.rest.notices.v1_0.domain.Messages;

public class MessagesUtils {

    public static Messages createMessagesList(String... messagesTexts) {
        Messages messages = new Messages();
        for (String text : messagesTexts) {
            messages.getMessages().add(MessageBuilder.message().withText(text).build());
        }

        messages.setTotal(new BigInteger(String.valueOf(messages.getMessages().size())));
        return messages;
    }
}
