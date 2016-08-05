package org.siemac.metamac.notices.web.client.presenter;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.navigation.shared.NameTokens;
import org.siemac.metamac.notices.web.client.AnnouncementCreationLoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.NoticesWeb;
import org.siemac.metamac.notices.web.client.enums.NoticesToolStripButtonEnum;
import org.siemac.metamac.notices.web.client.events.SelectMainSectionEvent;
import org.siemac.metamac.notices.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.notices.web.client.view.handlers.AnnouncementCreationUiHandlers;
import org.siemac.metamac.notices.web.shared.CreateNoticeAction;
import org.siemac.metamac.notices.web.shared.CreateNoticeResult;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsAction;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsResult;
import org.siemac.metamac.web.common.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

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
    @UseGatekeeper(AnnouncementCreationLoggedInGatekeeper.class)
    public interface AnnouncementCreationProxy extends Proxy<AnnouncementCreationPresenter>, Place {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContentConfiguration = new Type<RevealContentHandler<?>>();

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbAnnouncementCreation();
    }

    public interface AnnouncementCreationView extends View, HasUiHandlers<AnnouncementCreationUiHandlers> {

        void clearValues();
        void setStatisticalOperations(GetStatisticalOperationsResult result);
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
        getView().clearValues();
    }

    @Override
    public void create(NoticeDto notice) {
        dispatcher.execute(new CreateNoticeAction(notice), new WaitingAsyncCallbackHandlingError<CreateNoticeResult>(this) {

            @Override
            public void onWaitSuccess(CreateNoticeResult result) {
                fireSuccessMessage(NoticesWeb.getMessages().announcementCreated());
                placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteNoticesPlaceRequest());
            }
        });
    }

    @Override
    public void retrieveStatisticalOperations(PaginationWebCriteria criteria) {
        dispatcher.execute(new GetStatisticalOperationsAction(criteria), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationsResult result) {
                getView().setStatisticalOperations(result);
            }
        });
    }
}
