package org.siemac.metamac.notices.web.client.gin;

import org.siemac.metamac.notices.web.client.AnnouncementCreationLoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.NoticesLoggedInGatekeeper;
import org.siemac.metamac.notices.web.client.NoticesWebConstants;
import org.siemac.metamac.notices.web.client.NoticesWebMessages;
import org.siemac.metamac.notices.web.client.presenter.AnnouncementCreationPresenter;
import org.siemac.metamac.notices.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.notices.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.notices.web.client.presenter.NoticePresenter;
import org.siemac.metamac.notices.web.client.presenter.NoticesPresenter;
import org.siemac.metamac.notices.web.client.presenter.UnauthorizedPagePresenter;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;

@GinModules({DispatchAsyncModule.class, ClientModule.class})
public interface NoticesWebGinjector extends MetamacWebGinjector {

    NoticesLoggedInGatekeeper getNoticesLoggedInGatekeeper();
    AnnouncementCreationLoggedInGatekeeper getAnnouncementCreationLoggedInGatekeeper();

    Provider<MainPagePresenter> getMainPagePresenter();
    AsyncProvider<NoticesPresenter> getNoticesPresenter();
    AsyncProvider<NoticePresenter> getNoticePresenter();
    AsyncProvider<AnnouncementCreationPresenter> getAnnouncementCreationPresenter();

    AsyncProvider<ErrorPagePresenter> getErrorPagePresenter();
    AsyncProvider<UnauthorizedPagePresenter> getUnauthorizedPagePresenter();

    // Interfaces
    public NoticesWebConstants getNoticesWebConstants();
    public NoticesWebMessages getNoticesWebMessages();
}
