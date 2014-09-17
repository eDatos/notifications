package org.siemac.metamac.notices.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.notices.web.client.NameTokens;
import org.siemac.metamac.web.common.client.utils.CommonPlaceRequestUtils;

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
}
