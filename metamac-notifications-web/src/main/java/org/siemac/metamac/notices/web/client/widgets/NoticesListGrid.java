package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.utils.NoticesWebConstants.NOTICES_LIST_MAX_RESULTS;

import org.siemac.metamac.notices.web.client.utils.ResourceListFieldUtils;
import org.siemac.metamac.web.common.client.widgets.PaginatedCheckListGrid;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;

import com.smartgwt.client.types.Autofit;

public class NoticesListGrid extends PaginatedCheckListGrid {

    public NoticesListGrid(PaginatedAction action) {
        super(NOTICES_LIST_MAX_RESULTS, new BaseNoticeCustomListGrid(), action);
        getListGrid().setAutoFitMaxRecords(NOTICES_LIST_MAX_RESULTS);
        getListGrid().setAutoFitData(Autofit.VERTICAL);
        getListGrid().setFields(ResourceListFieldUtils.getNoticeFields());
    }
}
