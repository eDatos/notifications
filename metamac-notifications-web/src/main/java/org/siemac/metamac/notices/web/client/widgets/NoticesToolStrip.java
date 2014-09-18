package org.siemac.metamac.notices.web.client.widgets;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.web.client.utils.ClientSecurityUtils;

import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class NoticesToolStrip extends ToolStrip {

    private ToolStripButton markAsReadButton;
    private ToolStripButton markAsUnreadButton;

    public NoticesToolStrip() {
        setWidth100();

        markAsReadButton = new ToolStripButton(getConstants().actionMarkAsRead());
        markAsReadButton.setVisible(false);
        addButton(markAsReadButton);

        markAsUnreadButton = new ToolStripButton(getConstants().actionMarkAsUnread());
        markAsUnreadButton.setVisible(false);
        addButton(markAsUnreadButton);
    }

    public void showMarkAsReadButton() {
        if (ClientSecurityUtils.canMarkNoticeAsRead()) {
            markAsReadButton.show();
        }
    }

    public void showMarkAsUnreadButton() {
        if (ClientSecurityUtils.canMarkNoticeAsUnread()) {
            markAsUnreadButton.show();
        }
    }

    public void hideButtons() {
        markAsReadButton.hide();
        markAsUnreadButton.hide();
    }

    public HasClickHandlers getMarkAsReadButton() {
        return markAsReadButton;
    }

    public HasClickHandlers getMarkAsUnreadButton() {
        return markAsUnreadButton;
    }
}
