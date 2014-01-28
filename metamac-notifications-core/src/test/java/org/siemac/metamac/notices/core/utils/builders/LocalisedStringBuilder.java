package org.siemac.metamac.notices.core.utils.builders;

import org.siemac.metamac.notices.core.common.domain.LocalisedString;

public class LocalisedStringBuilder extends LocalisedStringBuilderBase<LocalisedStringBuilder> {

    public static LocalisedStringBuilder localisedString() {
        return new LocalisedStringBuilder();
    }

    public LocalisedStringBuilder() {
        super(new LocalisedString());
    }

    public LocalisedString build() {
        return getInstance();
    }
}

class LocalisedStringBuilderBase<GeneratorT extends LocalisedStringBuilderBase<GeneratorT>> {

    private final LocalisedString instance;

    protected LocalisedStringBuilderBase(LocalisedString aInstance) {
        instance = aInstance;
    }

    protected LocalisedString getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withLabel(String aValue) {
        instance.setLabel(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withLocale(String aValue) {
        instance.setLocale(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withIsUnmodifiable(Boolean aValue) {
        instance.setIsUnmodifiable(aValue);

        return (GeneratorT) this;
    }
}
