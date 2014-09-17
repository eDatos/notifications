package org.siemac.metamac.notices.web.client.view.handlers;

import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface NoticesUiHandlers extends UiHandlers {

    void retrieveNotices(NoticeWebCriteria criteria);
}
