package org.siemac.metamac.notices.web.client.events;

import org.siemac.metamac.notices.web.client.enums.NoticesToolStripButtonEnum;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class SelectMainSectionEvent extends GwtEvent<SelectMainSectionEvent.SelectMainSectionEventHandler> {

    public interface SelectMainSectionEventHandler extends EventHandler {

        void onSelectMainSection(SelectMainSectionEvent event);
    }

    private static Type<SelectMainSectionEventHandler> TYPE = new Type<SelectMainSectionEventHandler>();
    private final NoticesToolStripButtonEnum           buttonId;

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<SelectMainSectionEventHandler> getAssociatedType() {
        return TYPE;
    }

    public static void fire(HasHandlers source, NoticesToolStripButtonEnum buttonId) {
        if (TYPE != null) {
            source.fireEvent(new SelectMainSectionEvent(buttonId));
        }
    }

    public SelectMainSectionEvent(NoticesToolStripButtonEnum buttonId) {
        this.buttonId = buttonId;
    }

    public NoticesToolStripButtonEnum getButtonId() {
        return buttonId;
    }

    @Override
    protected void dispatch(SelectMainSectionEventHandler handler) {
        handler.onSelectMainSection(this);
    }

    public static Type<SelectMainSectionEventHandler> getType() {
        return TYPE;
    }
}
