package org.siemac.metamac.notices.web.client.widgets.view;

import static org.siemac.metamac.notices.web.client.NoticesWeb.getConstants;

import org.siemac.metamac.notices.web.client.enums.NoticesToolStripButtonEnum;
import org.siemac.metamac.notices.web.client.widgets.presenter.NoticesToolStripPresenterWidget.NoticesToolStripView;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.toolstrip.view.MetamacToolStripViewImpl;

import com.google.inject.Inject;
import com.smartgwt.client.widgets.events.HasClickHandlers;

public class NoticesToolStripViewImpl extends MetamacToolStripViewImpl implements NoticesToolStripView {

    private CustomToolStripButton noticesButton;
    private CustomToolStripButton announcementCreationButton;

    @Inject
    public NoticesToolStripViewImpl() {
        super();

        noticesButton = new CustomToolStripButton(getConstants().notices());
        noticesButton.setID(NoticesToolStripButtonEnum.NOTICES.getValue());

        announcementCreationButton = new CustomToolStripButton(getConstants().annoucementCreation());
        announcementCreationButton.setID(NoticesToolStripButtonEnum.ANNOUNCEMENT_CREATION.getValue());

        toolStrip.addButton(noticesButton);
        toolStrip.addButton(announcementCreationButton);
    }

    @Override
    public HasClickHandlers getGoNotices() {
        return noticesButton;
    }

    @Override
    public HasClickHandlers getGoAnnouncementCreation() {
        return announcementCreationButton;
    }
}
