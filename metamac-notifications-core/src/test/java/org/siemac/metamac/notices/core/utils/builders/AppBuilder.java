package org.siemac.metamac.notifications.core.utils.builders;

import org.siemac.metamac.notifications.core.notice.domain.App;

public class AppBuilder extends AppBuilderBase<AppBuilder> {

    public static AppBuilder app() {
        return new AppBuilder();
    }

    public AppBuilder() {
        super(new App());
    }

    public App build() {
        return getInstance();
    }
}

class AppBuilderBase<GeneratorT extends AppBuilderBase<GeneratorT>> {

    private App instance;

    protected AppBuilderBase(App aInstance) {
        instance = aInstance;
    }

    protected App getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withName(String aValue) {
        instance.setName(aValue);

        return (GeneratorT) this;
    }
}
