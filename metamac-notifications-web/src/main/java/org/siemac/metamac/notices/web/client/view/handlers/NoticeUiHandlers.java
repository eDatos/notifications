package org.siemac.metamac.notices.web.client.view.handlers;


import com.gwtplatform.mvp.client.UiHandlers;

public interface NoticeUiHandlers extends UiHandlers {
    void markAsRead(String noticeUrn);
    void markAsUnread(String noticeUrn);
}
