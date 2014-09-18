package org.siemac.metamac.notices.web.client.presenter;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.LoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.NameTokens;
import org.siemac.metamac.notices.web.client.enums.NoticesToolStripButtonEnum;
import org.siemac.metamac.notices.web.client.events.SelectMainSectionEvent;
import org.siemac.metamac.notices.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.notices.web.client.view.handlers.AnnouncementCreationUiHandlers;
import org.siemac.metamac.notices.web.shared.CreateNoticeAction;
import org.siemac.metamac.notices.web.shared.CreateNoticeResult;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class AnnouncementCreationPresenter extends Presenter<AnnouncementCreationPresenter.AnnouncementCreationView, AnnouncementCreationPresenter.AnnouncementCreationProxy>
        implements
            AnnouncementCreationUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.ANNOUNCEMENT_CREATION_PAGE)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface AnnouncementCreationProxy extends Proxy<AnnouncementCreationPresenter>, Place {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContentConfiguration = new Type<RevealContentHandler<?>>();

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbAnnouncementCreation();
    }

    public interface AnnouncementCreationView extends View, HasUiHandlers<AnnouncementCreationUiHandlers> {
    }

    @Inject
    public AnnouncementCreationPresenter(EventBus eventBus, AnnouncementCreationView view, AnnouncementCreationProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, MainPagePresenter.TYPE_SetContextAreaContent, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        SelectMainSectionEvent.fire(this, NoticesToolStripButtonEnum.ANNOUNCEMENT_CREATION);
        MainPagePresenter.getMasterHead().setTitleLabel(getConstants().annoucementCreation());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
    }

    @Override
    public void create(NoticeDto notice) {
        dispatcher.execute(new CreateNoticeAction(notice), new WaitingAsyncCallbackHandlingError<CreateNoticeResult>(this) {

            @Override
            public void onWaitSuccess(CreateNoticeResult result) {
                // TODO METAMAC-1984 show message with successful creation/send
                placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteNoticesPlaceRequest());
            }
        });
    }
}
