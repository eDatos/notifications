package org.siemac.metamac.notices.web.client.view.handlers;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.web.common.shared.criteria.PaginationWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface AnnouncementCreationUiHandlers extends UiHandlers {

    void create(NoticeDto notice);
    void retrieveStatisticalOperations(PaginationWebCriteria criteria);
}
