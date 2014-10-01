package org.siemac.metamac.notices.web.client.widgets;

import org.siemac.metamac.notices.web.client.model.NoticeRecord;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class BaseNoticeCustomListGrid extends CustomListGrid {

    public BaseNoticeCustomListGrid() {
        setAlternateRecordStyles(false);
        setCanSort(false);
    }

    @Override
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
        if (record instanceof NoticeRecord && !((NoticeRecord) record).getReceiverAcknowledge()) {
            return "font-weight:bold;";
        }
        return super.getCellCSSText(record, rowNum, colNum);
    }
}
