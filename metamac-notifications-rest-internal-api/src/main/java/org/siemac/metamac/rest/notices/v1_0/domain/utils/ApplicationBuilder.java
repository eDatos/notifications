package org.siemac.metamac.rest.notices.v1_0.domain.utils;

import org.siemac.metamac.rest.notifications.v1_0.domain.Application;

public class ApplicationBuilder extends ApplicationBuilderBase<ApplicationBuilder> {

    public static ApplicationBuilder application() {
        return new ApplicationBuilder();
    }

    public ApplicationBuilder() {
        super(new Application());
    }

    public Application build() {
        return getInstance();
    }
}

class ApplicationBuilderBase<GeneratorT extends ApplicationBuilderBase<GeneratorT>> {

    private final Application instance;

    protected ApplicationBuilderBase(Application aInstance) {
        instance = aInstance;
    }

    protected Application getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withName(String aValue) {
        instance.setName(aValue);

        return (GeneratorT) this;
    }
}
