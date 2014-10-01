package org.siemac.metamac.notices.core.criteria;

public enum NoticeCriteriaPropertyEnum {

    SUBJECT, ACKNOWLEDGE, SENDING_APPLICATION, SENDING_USER, TYPE, RECEIVER_USERNAME, EXPIRATION_DATE;

    public String value() {
        return name();
    }
    public static NoticeCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}
