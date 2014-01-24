package org.siemac.metamac.notifications.core.utils.builders;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.common.domain.InternationalString;

public class ExternalItemBuilder extends ExternalItemBuilderBase<ExternalItemBuilder> {

    public static ExternalItemBuilder externalItem() {
        return new ExternalItemBuilder();
    }

    public ExternalItemBuilder() {
        super(new ExternalItem());
    }

    public ExternalItem build() {
        return getInstance();
    }
}

class ExternalItemBuilderBase<GeneratorT extends ExternalItemBuilderBase<GeneratorT>> {

    private final ExternalItem instance;

    protected ExternalItemBuilderBase(ExternalItem aInstance) {
        instance = aInstance;
    }

    protected ExternalItem getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withCode(String aValue) {
        instance.setCode(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withCodeNested(String aValue) {
        instance.setCodeNested(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withUri(String aValue) {
        instance.setUri(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withUrn(String aValue) {
        instance.setUrn(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withUrnProvider(String aValue) {
        instance.setUrnProvider(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withManagementAppUrl(String aValue) {
        instance.setManagementAppUrl(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withType(TypeExternalArtefactsEnum aValue) {
        instance.setType(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withTitle(InternationalString aValue) {
        instance.setTitle(aValue);

        return (GeneratorT) this;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withTitle(String locale, String text) {
        InternationalString internationalString = InternationalStringBuilder.internationalString().withAddedLocalisedString(locale, text).build();
        instance.setTitle(internationalString);

        return (GeneratorT) this;
    }
}
