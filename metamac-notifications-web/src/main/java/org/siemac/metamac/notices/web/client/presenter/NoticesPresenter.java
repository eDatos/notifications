package org.siemac.metamac.notices.web.client.presenter;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.LoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.NameTokens;
import org.siemac.metamac.notices.web.client.NoticesWeb;
import org.siemac.metamac.notices.web.client.enums.NoticesToolStripButtonEnum;
import org.siemac.metamac.notices.web.client.events.SelectMainSectionEvent;
import org.siemac.metamac.notices.web.client.view.handlers.NoticesUiHandlers;
import org.siemac.metamac.notices.web.shared.GetNoticesAction;
import org.siemac.metamac.notices.web.shared.GetNoticesResult;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;
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

public class NoticesPresenter extends Presenter<NoticesPresenter.NoticesView, NoticesPresenter.NoticesProxy> implements NoticesUiHandlers {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.NOTICES_PAGE)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface NoticesProxy extends Proxy<NoticesPresenter>, Place {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContentConfiguration = new Type<RevealContentHandler<?>>();

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbNotices();
    }

    public interface NoticesView extends View, HasUiHandlers<NoticesUiHandlers> {

        void setNotices(List<NoticeDto> notices, int firstResult, int totalResults);
    }

    @Inject
    public NoticesPresenter(EventBus eventBus, NoticesView noticesView, NoticesProxy noticesProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, noticesView, noticesProxy);
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

        SelectMainSectionEvent.fire(this, NoticesToolStripButtonEnum.NOTICES);
        MainPagePresenter.getMasterHead().setTitleLabel(NoticesWeb.getConstants().notices());
        retrieveNotices(new NoticeWebCriteria());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        retrieveNotices(new NoticeWebCriteria());
    }

    @Override
    public void retrieveNotices(NoticeWebCriteria criteria) {
        dispatcher.execute(new GetNoticesAction(criteria), new WaitingAsyncCallbackHandlingError<GetNoticesResult>(this) {

            @Override
            public void onWaitSuccess(GetNoticesResult result) {
                getView().setNotices(result.getNotices(), result.getFirstResult(), result.getTotalResults());
            }
        });
    }
}
