package org.siemac.metamac.notices.web.client.enums;

import com.smartgwt.client.types.ValueEnum;

public enum NoticesToolStripButtonEnum implements ValueEnum {

    NOTICES("notices_button"), ANNOUNCEMENT_CREATION("announcement_creation_button");

    private String value;

    NoticesToolStripButtonEnum(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
