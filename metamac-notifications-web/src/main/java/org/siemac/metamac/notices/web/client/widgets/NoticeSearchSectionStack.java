package org.siemac.metamac.notices.web.client.widgets;

import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;
import org.siemac.metamac.web.common.client.widgets.SearchSectionStack;

public class NoticeSearchSectionStack extends SearchSectionStack {

    public NoticeWebCriteria getCriteria() {
        // TODO METAMAC-1984
        return new NoticeWebCriteria();
    }
}
