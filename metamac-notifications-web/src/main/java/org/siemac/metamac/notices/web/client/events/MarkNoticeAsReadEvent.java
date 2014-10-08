package org.siemac.metamac.notices.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class MarkNoticeAsReadEvent extends GwtEvent<MarkNoticeAsReadEvent.MarkNoticeAsReadHandler> {

    public interface MarkNoticeAsReadHandler extends EventHandler {

        void onMarkNoticeAsRead(MarkNoticeAsReadEvent event);
    }

    private static Type<MarkNoticeAsReadHandler> TYPE = new Type<MarkNoticeAsReadHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<MarkNoticeAsReadHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, String noticeUrn) {
        if (TYPE != null) {
            source.fireEvent(new MarkNoticeAsReadEvent(noticeUrn));
        }
    }

    private final String noticeUrn;

    public MarkNoticeAsReadEvent(String noticeUrn) {
        this.noticeUrn = noticeUrn;
    }

    public String getNoticeUrn() {
        return noticeUrn;
    }

    @Override
    protected void dispatch(MarkNoticeAsReadHandler handler) {
        handler.onMarkNoticeAsRead(this);
    }

    public static Type<MarkNoticeAsReadHandler> getType() {
        return TYPE;
    }
}
