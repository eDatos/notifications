package org.siemac.metamac.notifications.core.utils.builders;

import org.siemac.metamac.notifications.core.notice.domain.Role;

public class RoleBuilder extends RoleBuilderBase<RoleBuilder> {

    public static RoleBuilder role() {
        return new RoleBuilder();
    }

    public RoleBuilder() {
        super(new Role());
    }

    public Role build() {
        return getInstance();
    }
}

class RoleBuilderBase<GeneratorT extends RoleBuilderBase<GeneratorT>> {

    private final Role instance;

    protected RoleBuilderBase(Role aInstance) {
        instance = aInstance;
    }

    protected Role getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GeneratorT withName(String aValue) {
        instance.setName(aValue);

        return (GeneratorT) this;
    }
}
