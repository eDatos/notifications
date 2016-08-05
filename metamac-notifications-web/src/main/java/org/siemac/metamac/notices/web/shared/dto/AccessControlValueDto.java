package org.siemac.metamac.notices.web.shared.dto;

import java.io.Serializable;

public class AccessControlValueDto implements Serializable {

    private static final long serialVersionUID = 2623296262269615754L;

    private String            code;
    private String            title;
    private String            description;

    public AccessControlValueDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
