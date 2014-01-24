package org.siemac.metamac.notifications.core.utils.builders;

import org.siemac.metamac.notifications.core.common.domain.InternationalString;
import org.siemac.metamac.notifications.core.common.domain.LocalisedString;

public class InternationalStringBuilder extends InternationalStringBuilderBase<InternationalStringBuilder> {

    public static InternationalStringBuilder internationalString() {
        return new InternationalStringBuilder();
    }

    public InternationalStringBuilder() {
        super(new InternationalString());
    }

    public InternationalString build() {
        return getInstance();
    }
}

class InternationalStringBuilderBase<GeneratorT extends InternationalStringBuilderBase<GeneratorT>> {

    private final InternationalString instance;

    protected InternationalStringBuilderBase(InternationalString aInstance) {
        instance = aInstance;
    }

    protected InternationalString getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedLocalisedString(String locale, String text) {
        LocalisedString localisedString = LocalisedStringBuilder.localisedString().withLocale(locale).withLabel(text).withIsUnmodifiable(Boolean.FALSE).build();
        instance.getTexts().add(localisedString);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withAddedLocalisedString(LocalisedString localisedString) {
        instance.getTexts().add(localisedString);
        return (GeneratorT) this;
    }

}
