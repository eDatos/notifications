package org.siemac.metamac.notices.web.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.notices.core.navigation.shared.NameTokens;
import org.siemac.metamac.notices.web.client.events.SelectMainSectionEvent;
import org.siemac.metamac.notices.web.client.events.SelectMainSectionEvent.SelectMainSectionEventHandler;
import org.siemac.metamac.notices.web.client.view.handlers.MainPageUiHandlers;
import org.siemac.metamac.notices.web.client.widgets.presenter.NoticesToolStripPresenterWidget;
import org.siemac.metamac.notices.web.shared.GetHelpUrlAction;
import org.siemac.metamac.notices.web.shared.GetHelpUrlResult;
import org.siemac.metamac.web.common.client.enums.MessageTypeEnum;
import org.siemac.metamac.web.common.client.events.ChangeWaitPopupVisibilityEvent;
import org.siemac.metamac.web.common.client.events.ChangeWaitPopupVisibilityEvent.ChangeWaitPopupVisibilityHandler;
import org.siemac.metamac.web.common.client.events.HideMessageEvent;
import org.siemac.metamac.web.common.client.events.HideMessageEvent.HideMessageHandler;
import org.siemac.metamac.web.common.client.events.SetTitleEvent;
import org.siemac.metamac.web.common.client.events.SetTitleEvent.SetTitleHandler;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent.ShowMessageHandler;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.client.widgets.BreadCrumbsPanel;
import org.siemac.metamac.web.common.client.widgets.MasterHead;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.CloseSessionResult;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.mvp.client.proxy.SetPlaceTitleHandler;

public class MainPagePresenter extends Presenter<MainPagePresenter.MainPageView, MainPagePresenter.MainPageProxy>
        implements
            MainPageUiHandlers,
            ShowMessageHandler,
            HideMessageHandler,
            SetTitleHandler,
            SelectMainSectionEventHandler,
            ChangeWaitPopupVisibilityHandler {

    private static Logger                             logger                 = Logger.getLogger(MainPagePresenter.class.getName());

    private final PlaceManager                        placeManager;
    private final DispatchAsync                       dispatcher;

    private static MasterHead                         masterHead;

    private final NoticesToolStripPresenterWidget     toolStripPresenterWidget;

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetNoticesToolBar = new Type<RevealContentHandler<?>>();

    @ProxyStandard
    @NameToken(NameTokens.MAIN_PAGE)
    @NoGatekeeper
    public interface MainPageProxy extends Proxy<MainPagePresenter>, Place {

    }

    public interface MainPageView extends View, HasUiHandlers<MainPageUiHandlers> {

        MasterHead getMasterHead();

        BreadCrumbsPanel getBreadCrumbsPanel();
        void clearBreadcrumbs(int size, PlaceManager placeManager);
        void setBreadcrumb(int index, String title);
        void showMessage(Throwable throwable, String message, MessageTypeEnum type);
        void hideMessages();
        void setTitle(String title);

        void showWaitPopup();
        void hideWaitPopup();
    }

    /**
     * Use this in leaf presenters, inside their {@link #revealInParent} method.
     * Is used to define a type to use in child presenters when you want to
     * include them inside this page.
     */
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContent = new Type<RevealContentHandler<?>>();

    @Inject
    public MainPagePresenter(EventBus eventBus, MainPageView view, MainPageProxy proxy, PlaceManager placeManager, DispatchAsync dispatcher,
            NoticesToolStripPresenterWidget noticesToolStripPresenterWidget) {
        super(eventBus, view, proxy);
        this.toolStripPresenterWidget = noticesToolStripPresenterWidget;
        getView().setUiHandlers(this);
        this.placeManager = placeManager;
        this.dispatcher = dispatcher;
        MainPagePresenter.masterHead = getView().getMasterHead();
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
    }

    @Override
    protected void onReveal() {
        super.onReveal();
        setInSlot(TYPE_SetNoticesToolBar, toolStripPresenterWidget);
    }

    @Override
    protected void onReset() {
        super.onReset();
        updateBreadcrumbs();
    }

    private void updateBreadcrumbs() {
        int size = placeManager.getHierarchyDepth();
        getView().clearBreadcrumbs(size, placeManager);
        for (int i = 0; i < size; ++i) {
            final int index = i;
            placeManager.getTitle(i, new SetPlaceTitleHandler() {

                @Override
                public void onSetPlaceTitle(String title) {
                    getView().setBreadcrumb(index, title);
                }
            });
        }
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }

    @Override
    public void onNavigationPaneSectionHeaderClicked(String place) {
        if (place.length() != 0) {
            PlaceRequest placeRequest = new PlaceRequest(place);
            placeManager.revealPlace(placeRequest);
        }
    }

    @Override
    public void onNavigationPaneSectionClicked(String place) {
        if (place.length() != 0) {
            PlaceRequest placeRequest = new PlaceRequest(place);
            placeManager.revealPlace(placeRequest);
        }
    }

    @ProxyEvent
    @Override
    public void onSelectMainSection(SelectMainSectionEvent event) {
        toolStripPresenterWidget.deselectButtons();
        toolStripPresenterWidget.selectButton(event.getButtonId().getValue());
    }

    public static MasterHead getMasterHead() {
        return masterHead;
    }

    @ProxyEvent
    @Override
    public void onShowMessage(ShowMessageEvent event) {
        getView().showMessage(event.getThrowable(), event.getMessage(), event.getMessageType());
    }

    @ProxyEvent
    @Override
    public void onHideMessage(HideMessageEvent event) {
        hideMessages();
    }

    @ProxyEvent
    @Override
    public void onSetTitle(SetTitleEvent event) {
        getView().setTitle(event.getTitle());
    }

    @ProxyEvent
    @Override
    public void onChangeWaitPopupVisibility(ChangeWaitPopupVisibilityEvent event) {
        if (event.isShowPopup()) {
            getView().showWaitPopup();
        } else {
            getView().hideWaitPopup();
        }
    }

    @Override
    public void closeSession() {
        dispatcher.execute(new CloseSessionAction(), new AsyncCallback<CloseSessionResult>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error closing session");
                ShowMessageEvent.fireErrorMessage(MainPagePresenter.this, caught);
            }
            @Override
            public void onSuccess(CloseSessionResult result) {
                Window.Location.assign(result.getLogoutPageUrl());
            }
        });
    }

    @Override
    public void openHelpUrl() {
        dispatcher.execute(new GetHelpUrlAction(), new WaitingAsyncCallbackHandlingError<GetHelpUrlResult>(this) {

            @Override
            public void onWaitSuccess(GetHelpUrlResult result) {
                Window.open(result.getHelpUrl(), "_blank", "");
            }
        });
    }

    private void hideMessages() {
        getView().hideMessages();
    }

    protected List<PlaceRequest> getHierarchyUntilNameToken(String nameToken) {
        List<PlaceRequest> filteredHierarchy = new ArrayList<PlaceRequest>();
        List<PlaceRequest> hierarchy = placeManager.getCurrentPlaceHierarchy();
        boolean found = false;
        for (int i = 0; i < hierarchy.size() && !found; i++) {
            PlaceRequest placeReq = hierarchy.get(i);
            if (placeReq.matchesNameToken(nameToken)) {
                found = true;
            }
            filteredHierarchy.add(placeReq);
        }

        return filteredHierarchy;
    }
}
