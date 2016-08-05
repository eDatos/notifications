package org.siemac.metamac.notices.web.client.view.handlers;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface NoticesUiHandlers extends UiHandlers {

    void retrieveNotices(NoticeWebCriteria criteria);
    void goToNotice(String urn);
    void markAsRead(List<NoticeDto> notices);
    void markAsUnread(List<NoticeDto> notices);
}
