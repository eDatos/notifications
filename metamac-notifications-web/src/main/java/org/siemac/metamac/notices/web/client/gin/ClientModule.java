package org.siemac.metamac.notices.web.client.gin;

import org.siemac.metamac.notices.web.client.LoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.NameTokens;
import org.siemac.metamac.notices.web.client.NoticesPlaceManager;
import org.siemac.metamac.notices.web.client.NoticesWebConstants;
import org.siemac.metamac.notices.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.notices.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.notices.web.client.presenter.NoticesPresenter;
import org.siemac.metamac.notices.web.client.presenter.UnauthorizedPagePresenter;
import org.siemac.metamac.notices.web.client.view.ErrorPageViewImpl;
import org.siemac.metamac.notices.web.client.view.MainPageViewImpl;
import org.siemac.metamac.notices.web.client.view.NoticesViewImpl;
import org.siemac.metamac.notices.web.client.view.UnauthorizedPageViewImpl;
import org.siemac.metamac.notices.web.client.widgets.presenter.NoticesToolStripPresenterWidget;
import org.siemac.metamac.notices.web.client.widgets.view.NoticesToolStripViewImpl;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractPresenterModule {

    @Override
    protected void configure() {
        // Default implementation of standard resources
        // |_ bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        // |_ bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(Singleton.class);
        // |_ bind(RootPresenter.class).asEagerSingleton();
        // |_ bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
        // |_ bind(GoogleAnalytics.class).to(GoogleAnalyticsImpl.class).in(Singleton.class);
        install(new DefaultModule(NoticesPlaceManager.class));

        // Constants
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.NOTICES_PAGE);

        // Gate keeper
        bind(LoggedInGatekeeper.class).in(Singleton.class);

        // Presenters
        bindPresenter(MainPagePresenter.class, MainPagePresenter.MainPageView.class, MainPageViewImpl.class, MainPagePresenter.MainPageProxy.class);
        bindPresenter(NoticesPresenter.class, NoticesPresenter.NoticesView.class, NoticesViewImpl.class, NoticesPresenter.NoticesProxy.class);

        // Error pages
        bindPresenter(ErrorPagePresenter.class, ErrorPagePresenter.ErrorPageView.class, ErrorPageViewImpl.class, ErrorPagePresenter.ErrorPageProxy.class);
        bindPresenter(UnauthorizedPagePresenter.class, UnauthorizedPagePresenter.UnauthorizedPageView.class, UnauthorizedPageViewImpl.class, UnauthorizedPagePresenter.UnauthorizedPageProxy.class);

        // Presenter widgets
        bindSingletonPresenterWidget(NoticesToolStripPresenterWidget.class, NoticesToolStripPresenterWidget.NoticesToolStripView.class, NoticesToolStripViewImpl.class);

        // Interfaces
        bind(NoticesWebConstants.class).in(Singleton.class);
    }
}
