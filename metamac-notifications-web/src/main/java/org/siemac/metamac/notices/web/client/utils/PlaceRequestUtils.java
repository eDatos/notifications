package org.siemac.metamac.notices.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.notices.core.navigation.shared.NameTokens;
import org.siemac.metamac.notices.core.navigation.shared.PlaceRequestParams;
import org.siemac.metamac.web.common.client.utils.CommonPlaceRequestUtils;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PlaceRequestUtils extends CommonPlaceRequestUtils {

    public static List<PlaceRequest> buildAbsoluteNoticesPlaceRequest() {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        PlaceRequest configurationsPlace = new PlaceRequest(NameTokens.NOTICES_PAGE);
        placeRequests.add(configurationsPlace);
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteAnnouncementCreationPlaceRequest() {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        PlaceRequest configurationsPlace = new PlaceRequest(NameTokens.ANNOUNCEMENT_CREATION_PAGE);
        placeRequests.add(configurationsPlace);
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteNoticePlaceRequest(String noticeUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteNoticesPlaceRequest();
        PlaceRequest noticePlace = buildRelativeNoticePlaceRequest(noticeUrn);
        placeRequests.add(noticePlace);
        return placeRequests;
    }

    public static PlaceRequest buildRelativeNoticePlaceRequest(String noticeUrn) {
        return new PlaceRequest(NameTokens.NOTICE_PAGE).with(PlaceRequestParams.noticeParamId, UrnUtils.removePrefix(noticeUrn));
    }

    public static String getNoticeParamFromUrl(PlaceManager placeManager) {
        return getParamFromUrl(placeManager, NameTokens.NOTICE_PAGE, PlaceRequestParams.noticeParamId);
    }
}
