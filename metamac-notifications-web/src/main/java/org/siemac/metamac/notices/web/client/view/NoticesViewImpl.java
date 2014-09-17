package org.siemac.metamac.notices.web.client.view;

import static org.siemac.metamac.web.common.client.constants.CommonWebConstants.MAIN_LIST_MAX_RESULTS;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.client.model.NoticeRecord;
import org.siemac.metamac.notices.web.client.presenter.NoticesPresenter;
import org.siemac.metamac.notices.web.client.utils.RecordUtils;
import org.siemac.metamac.notices.web.client.utils.ResourceListFieldUtils;
import org.siemac.metamac.notices.web.client.view.handlers.NoticesUiHandlers;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class NoticesViewImpl extends ViewWithUiHandlers<NoticesUiHandlers> implements NoticesPresenter.NoticesView {

    private VLayout                panel;
    private ToolStrip              toolStrip;
    private PaginatedCheckListGrid noticesListGrid;

    private SearchSectionStack     searchSectionStack;

    @Inject
    public NoticesViewImpl() {
        panel = new VLayout();
        createSearchSectionStack();
        createToolStrip();
        createNoticesListGrid();
        VLayout subPanel = new VLayout();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.addMember(toolStrip);
        subPanel.addMember(searchSectionStack);
        subPanel.addMember(noticesListGrid);

        panel.addMember(subPanel);
    }

    private void createSearchSectionStack() {
        searchSectionStack = new SearchSectionStack();
    }

    private void createToolStrip() {
        toolStrip = new ToolStrip();
    }

    private void createNoticesListGrid() {
        noticesListGrid = new PaginatedCheckListGrid(MAIN_LIST_MAX_RESULTS, new PaginatedAction() {

            @Override
            public void retrieveResultSet(int firstResult, int maxResults) {
                // TODO METAMAC-1984
            }
        });
        noticesListGrid.getListGrid().setAutoFitMaxRecords(MAIN_LIST_MAX_RESULTS);
        noticesListGrid.getListGrid().setAutoFitData(Autofit.VERTICAL);
        noticesListGrid.getListGrid().setFields(ResourceListFieldUtils.getNoticeFields());
        noticesListGrid.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                // TODO METAMAC-1984
            }
        });
    }

    @Override
    public void setNotices(List<NoticeDto> notices, int firstResult, int totalResults) {
        NoticeRecord[] records = RecordUtils.getNoticesRecords(notices);
        noticesListGrid.getListGrid().setData(records);
        noticesListGrid.refreshPaginationInfo(firstResult, records.length, totalResults);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
