package org.siemac.metamac.notices.web.client;

import org.siemac.metamac.notices.core.navigation.shared.NameTokens;
import org.siemac.metamac.notices.web.client.gin.DefaultPlace;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManagerImpl;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;

public class NoticesPlaceManager extends PlaceManagerImpl {

    private final PlaceRequest defaultPlaceRequest;

    @Inject
    public NoticesPlaceManager(EventBus eventBus, TokenFormatter tokenFormatter, @DefaultPlace String defaultNameToken) {
        super(eventBus, tokenFormatter);
        this.defaultPlaceRequest = new PlaceRequest(defaultNameToken);
    }

    @Override
    public void revealDefaultPlace() {
        revealPlace(defaultPlaceRequest);
    }

    @Override
    public void revealUnauthorizedPlace(String unauthorizedHistoryToken) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.UNAUTHORIZED_ACCESS_PAGE);
        placeRequest = placeRequest.with("redirect", unauthorizedHistoryToken);
        revealPlace(placeRequest);
    }

    @Override
    public void revealErrorPlace(String invalidHistoryToken) {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.ERROR_PAGE);
        revealPlace(placeRequest);
    }
}
