package org.siemac.metamac.notices.web.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class HideNoticeEvent extends GwtEvent<HideNoticeEvent.HideNoticeHandler> {

    public interface HideNoticeHandler extends EventHandler {

        void onHideNotice(HideNoticeEvent event);
    }

    private static Type<HideNoticeHandler> TYPE = new Type<HideNoticeHandler>();

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<HideNoticeHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source) {
        if (TYPE != null) {
            source.fireEvent(new HideNoticeEvent());
        }
    }

    public HideNoticeEvent() {
    }

    @Override
    protected void dispatch(HideNoticeHandler handler) {
        handler.onHideNotice(this);
    }

    public static Type<HideNoticeHandler> getType() {
        return TYPE;
    }
}
