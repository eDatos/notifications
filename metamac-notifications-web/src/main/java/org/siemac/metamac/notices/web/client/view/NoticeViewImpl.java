package org.siemac.metamac.notices.web.client.view;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.presenter.NoticePresenter;
import org.siemac.metamac.notices.web.client.view.handlers.NoticeUiHandlers;
import org.siemac.metamac.notices.web.client.widgets.NoticeLayout;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.VLayout;

public class NoticeViewImpl extends ViewWithUiHandlers<NoticeUiHandlers> implements NoticePresenter.NoticeView {

    private VLayout      panel;
    private NoticeLayout noticeLayout;

    @Inject
    public NoticeViewImpl() {

        noticeLayout = new NoticeLayout();

        panel = new VLayout();
        panel.addMember(noticeLayout);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setNotice(NoticeDto notice) {
        noticeLayout.setNotice(notice);
    }

    @Override
    public void hideNotice() {
        noticeLayout.hide();
    }
}
