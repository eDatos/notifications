package org.siemac.metamac.notices.web.client.view;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.presenter.NoticePresenter;
import org.siemac.metamac.notices.web.client.view.handlers.NoticeUiHandlers;
import org.siemac.metamac.notices.web.client.widgets.NoticeLayout;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class NoticeViewImpl extends ViewWithUiHandlers<NoticeUiHandlers> implements NoticePresenter.NoticeView {

    private VLayout          panel;
    private NoticeLayout     noticeLayout;

    @Inject
    public NoticeViewImpl() {
        
        noticeLayout = new NoticeLayout();
        addNoticeLayoutToolStripHandlers();

        panel = new VLayout();
        panel.addMember(noticeLayout);
    }

    private void addNoticeLayoutToolStripHandlers() {
        noticeLayout.getNoticeToolStrip().getMarkAsReadButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().markAsRead(noticeLayout.getNoticeUrn());
            }
        });
        noticeLayout.getNoticeToolStrip().getMarkAsUnreadButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().markAsUnread(noticeLayout.getNoticeUrn());
            }
        });
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
