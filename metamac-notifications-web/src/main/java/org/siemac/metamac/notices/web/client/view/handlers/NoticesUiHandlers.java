package org.siemac.metamac.notices.web.client.view.handlers;

import java.util.List;

import org.siemac.metamac.notices.core.dto.NoticeDto;
import org.siemac.metamac.notices.web.shared.criteria.NoticeWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;

public interface NoticesUiHandlers extends UiHandlers {

    void retrieveNotices(NoticeWebCriteria criteria);
    void retrieveNotice(NoticeDto notice, NoticeWebCriteria criteria);
    void markAsRead(List<NoticeDto> notices);
    void markAsUnread(List<NoticeDto> notices);
}
