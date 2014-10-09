package org.siemac.metamac.notices.core.navigation;

import java.text.MessageFormat;

import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.notices.core.navigation.shared.NameTokens;
import org.siemac.metamac.notices.core.navigation.shared.PlaceRequestParams;

public class InternalWebApplicationNavigation {

    private static final String ANCHOR    = "#";
    private static final String SEPARATOR = "/";
    private static final String SEMICOLON = ";";
    private static final String EQUALS    = "=";

    private static final String noticePattern;

    static {
        noticePattern = "{0}" + SEPARATOR + ANCHOR + NameTokens.NOTICES_PAGE + SEPARATOR + NameTokens.NOTICE_PAGE + SEMICOLON + PlaceRequestParams.noticeParamId + EQUALS + "{1}";
    }

    public static String buildNoticeUrl(String webApplicationPath, String noticeUrn) {
        String noticeIdentifier = UrnUtils.removePrefix(noticeUrn);
        return MessageFormat.format(noticePattern, webApplicationPath, noticeIdentifier);
    }
}
