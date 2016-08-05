package org.siemac.metamac.notices.web.client.presenter;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import java.util.Arrays;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.core.navigation.shared.NameTokens;
import org.siemac.metamac.notices.web.client.NoticesLoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.NoticesWeb;
import org.siemac.metamac.notices.web.client.enums.NoticesToolStripButtonEnum;
import org.siemac.metamac.notices.web.client.events.HideNoticeEvent;
import org.siemac.metamac.notices.web.client.events.HideNoticeEvent.HideNoticeHandler;
import org.siemac.metamac.notices.web.client.events.MarkNoticeAsReadEvent;
import org.siemac.metamac.notices.web.client.events.SelectMainSectionEvent;
import org.siemac.metamac.notices.web.client.utils.CommonUtils;
import org.siemac.metamac.notices.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.notices.web.client.view.handlers.NoticeUiHandlers;
import org.siemac.metamac.notices.web.shared.GetNoticeAction;
import org.siemac.metamac.notices.web.shared.GetNoticeResult;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeAction;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeResult;
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
import com.gwtplatform.mvp.client.annotations.ProxyEvent;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class NoticePresenter extends Presenter<NoticePresenter.NoticeView, NoticePresenter.NoticeProxy> implements NoticeUiHandlers, HideNoticeHandler {

    private final DispatchAsync dispatcher;
    private final PlaceManager  placeManager;

    @ProxyCodeSplit
    @NameToken(NameTokens.NOTICE_PAGE)
    @UseGatekeeper(NoticesLoggedInGatekeeper.class)
    public interface NoticeProxy extends Proxy<NoticePresenter>, Place {
    }

    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetContextAreaContentConfiguration = new Type<RevealContentHandler<?>>();

    @TitleFunction
    public static String getTranslatedTitle() {
        return getConstants().breadcrumbNotice();
    }

    public interface NoticeView extends View, HasUiHandlers<NoticeUiHandlers> {

        void setNotice(NoticeDto notice);
        void hideNotice();
    }

    @Inject
    public NoticePresenter(EventBus eventBus, NoticeView noticeView, NoticeProxy noticeProxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, noticeView, noticeProxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, NoticesPresenter.TYPE_SetContextAreaContentConfiguration, this);
    }

    @Override
    protected void onReveal() {
        super.onReveal();

        SelectMainSectionEvent.fire(this, NoticesToolStripButtonEnum.NOTICES);
        MainPagePresenter.getMasterHead().setTitleLabel(NoticesWeb.getConstants().notices());
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String identifier = PlaceRequestUtils.getNoticeParamFromUrl(placeManager);
        if (!StringUtils.isBlank(identifier)) {
            retrieveNotice(identifier);
        } else {
            NoticesWeb.showErrorPage();
        }
    }

    @ProxyEvent
    @Override
    public void onHideNotice(HideNoticeEvent event) {
        getView().hideNotice();
    }

    public void retrieveNotice(String noticeIdentifier) {
        final String urn = CommonUtils.generateNoticeUrn(noticeIdentifier);
        dispatcher.execute(new GetNoticeAction(urn), new WaitingAsyncCallbackHandlingError<GetNoticeResult>(this) {

            @Override
            public void onWaitSuccess(GetNoticeResult result) {
                getView().setNotice(result.getUpdatedNotice());
                MarkNoticeAsReadEvent.fire(NoticePresenter.this, urn);
            }
        });
    }

    @Override
    public void markAsRead(String noticeUrn) {
        updateNoticeReceiverStatus(noticeUrn, true);  
        
    }

    @Override
    public void markAsUnread(String noticeUrn) {
        updateNoticeReceiverStatus(noticeUrn, false);
    }   
    
    private void updateNoticeReceiverStatus(String noticeUrn, boolean receiverAcknowledgeStatus) {
        // Nothing else is needed on UpdateNoticeRecieverAcknowledge and this way we can reuse the existing method
        NoticeDto notice = new NoticeDto();
        notice.setUrn(noticeUrn);
        
        dispatcher.execute(new UpdateNoticeRecieverAcknowledgeAction(Arrays.asList(notice), receiverAcknowledgeStatus), new WaitingAsyncCallbackHandlingError<UpdateNoticeRecieverAcknowledgeResult>(this) {

            @Override
            public void onWaitSuccess(UpdateNoticeRecieverAcknowledgeResult result) {
                placeManager.revealPlaceHierarchy(PlaceRequestUtils.buildAbsoluteNoticesPlaceRequest());     
            }
        });
    }
}
