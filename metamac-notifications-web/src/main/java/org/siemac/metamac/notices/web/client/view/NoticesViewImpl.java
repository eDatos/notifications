package org.siemac.metamac.notices.web.client.view;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.model.NoticeRecord;
import org.siemac.metamac.notices.web.client.presenter.NoticesPresenter;
import org.siemac.metamac.notices.web.client.utils.RecordUtils;
import org.siemac.metamac.notices.web.client.view.handlers.NoticesUiHandlers;
import org.siemac.metamac.notices.web.client.widgets.NoticeLayout;
import org.siemac.metamac.notices.web.client.widgets.NoticesListGrid;
import org.siemac.metamac.notices.web.client.widgets.NoticesSearchSectionStack;
import org.siemac.metamac.notices.web.client.widgets.NoticesToolStrip;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;

public class NoticesViewImpl extends ViewWithUiHandlers<NoticesUiHandlers> implements NoticesPresenter.NoticesView {

    private VLayout                   panel;
    private NoticesSearchSectionStack searchSectionStack;
    private NoticesToolStrip          toolStrip;
    private NoticesListGrid           noticesListGrid;
    private NoticeLayout              noticeLayout;

    @Inject
    public NoticesViewImpl() {

        createSearchSectionStack();
        createToolStrip();
        createNoticesListGrid();
        createNoticeLayout();

        VLayout subPanel = new VLayout();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.addMember(searchSectionStack);
        subPanel.addMember(toolStrip);
        subPanel.addMember(noticesListGrid);
        subPanel.addMember(noticeLayout);

        panel = new VLayout();
        panel.addMember(subPanel);
    }

    @Override
    public void setNotices(List<NoticeDto> notices, int firstResult, int totalResults) {
        NoticeRecord[] records = RecordUtils.getNoticesRecords(notices);
        noticesListGrid.getListGrid().setData(records);
        noticesListGrid.refreshPaginationInfo(firstResult, records.length, totalResults);
    }

    @Override
    public void setNotice(NoticeDto notice) {
        noticeLayout.setNotice(notice);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(NoticesUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        searchSectionStack.setUiHandlers(uiHandlers);
    }

    @Override
    public NoticeWebCriteria getCurrentCriteria() {
        NoticeWebCriteria criteria = searchSectionStack.getNoticeWebCriteria();
        criteria.setFirstResult(noticesListGrid.getFirstResult());
        return criteria;
    }

    private void createSearchSectionStack() {
        searchSectionStack = new NoticesSearchSectionStack();
    }

    private void createToolStrip() {
        toolStrip = new NoticesToolStrip();
        toolStrip.getMarkAsReadButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().markAsRead(RecordUtils.getNoticeDtos(noticesListGrid.getListGrid().getSelectedRecords()));
            }
        });
        toolStrip.getMarkAsUnreadButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().markAsUnread(RecordUtils.getNoticeDtos(noticesListGrid.getListGrid().getSelectedRecords()));
            }
        });
    }

    private void createNoticesListGrid() {
        noticesListGrid = new NoticesListGrid(new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                NoticeWebCriteria criteria = searchSectionStack.getNoticeWebCriteria();
                criteria.setFirstResult(firstResult);
                criteria.setMaxResults(maxResults);
                getUiHandlers().retrieveNotices(criteria);
            }
        });

        noticesListGrid.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                updateToolStripButtonsVisibility(noticesListGrid.getListGrid().getSelectedRecords());
            }
        });

        noticesListGrid.getListGrid().addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getFieldNum() != 0) {
                    selectNotice(noticesListGrid.getListGrid().getSelectedRecords());
                }
            }
        });
    }

    private void createNoticeLayout() {
        noticeLayout = new NoticeLayout();
    }

    private void selectNotice(ListGridRecord[] selectedRecords) {
        if (selectedRecords != null && selectedRecords.length == 1) {
            if (selectedRecords[0] instanceof NoticeRecord) {
                getUiHandlers().retrieveNotice(((NoticeRecord) selectedRecords[0]).getNoticeDto(), getCurrentCriteria());
            }
        }
    }

    private void updateToolStripButtonsVisibility(ListGridRecord[] selectedRecords) {
        toolStrip.hideButtons();
        if (selectedRecords != null && selectedRecords.length > 0) {
            boolean allNoticesRead = true;
            boolean allNoticesUnread = true;
            for (ListGridRecord record : selectedRecords) {
                if (record instanceof NoticeRecord) {
                    boolean noticeRead = ((NoticeRecord) record).getReceiverAcknowledge();
                    if (noticeRead) {
                        allNoticesUnread = false;
                    } else {
                        allNoticesRead = false;
                    }
                }
            }
            if (allNoticesRead) {
                toolStrip.showMarkAsUnreadButton();
            } else if (allNoticesUnread) {
                toolStrip.showMarkAsReadButton();
            }
        }
    }
}
