package org.siemac.metamac.notices.core.criteria;

public enum NoticeCriteriaOrderEnum {

    CREATED_DATE;

    public String value() {
        return name();
    }

    public static NoticeCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }
}
