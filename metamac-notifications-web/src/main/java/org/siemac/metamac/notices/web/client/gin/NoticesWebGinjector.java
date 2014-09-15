package org.siemac.metamac.notices.web.client.gin;

import org.siemac.metamac.notices.web.client.LoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.notices.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.notices.web.client.presenter.UnauthorizedPagePresenter;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;

@GinModules({DispatchAsyncModule.class, ClientModule.class})
public interface NoticesWebGinjector extends MetamacWebGinjector {

    LoggedInGatekeeper getLoggedInGatekeeper();

    Provider<MainPagePresenter> getMainPagePresenter();

    AsyncProvider<ErrorPagePresenter> getErrorPagePresenter();
    AsyncProvider<UnauthorizedPagePresenter> getUnauthorizedPagePresenter();
}
