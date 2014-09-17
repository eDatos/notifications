package org.siemac.metamac.notices.web.client.widgets.presenter;

import org.siemac.metamac.notices.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.web.common.client.widgets.toolstrip.presenter.MetamacToolStripPresenterWidget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class NoticesToolStripPresenterWidget extends MetamacToolStripPresenterWidget<NoticesToolStripPresenterWidget.NoticesToolStripView> {

    public interface NoticesToolStripView extends MetamacToolStripPresenterWidget.MetamacToolStripView {

        HasClickHandlers getGoNotices();
        HasClickHandlers getGoAnnouncementCreation();
    }

    @Inject
    public NoticesToolStripPresenterWidget(EventBus eventBus, NoticesToolStripView toolStripView, PlaceManager placeManager) {
        super(eventBus, toolStripView, placeManager);
    }

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(getView().getGoNotices().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteNoticesPlaceRequest());
            }
        }));

        registerHandler(getView().getGoAnnouncementCreation().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getPlaceManager().revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteAnnouncementCreationPlaceRequest());
            }
        }));
    }
}
