package org.siemac.metamac.notices.web.client.view;

import org.siemac.metamac.notices.web.client.presenter.AnnouncementCreationPresenter;
import org.siemac.metamac.notices.web.client.view.handlers.AnnouncementCreationUiHandlers;
import org.siemac.metamac.notices.web.client.widgets.AnnouncementCreationLayout;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsResult;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class AnnouncementCreationViewImpl extends ViewWithUiHandlers<AnnouncementCreationUiHandlers> implements AnnouncementCreationPresenter.AnnouncementCreationView {

    private VLayout                    panel;
    private AnnouncementCreationLayout announcementCreationLayout;

    @Inject
    public AnnouncementCreationViewImpl() {

        announcementCreationLayout = new AnnouncementCreationLayout();
        announcementCreationLayout.getSendButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().create(announcementCreationLayout.getNotice());
            }
        });

        panel = new VLayout();
        panel.addMember(announcementCreationLayout);
    }

    @Override
    public void setUiHandlers(AnnouncementCreationUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        announcementCreationLayout.setUiHandlers(uiHandlers);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setStatisticalOperations(GetStatisticalOperationsResult result) {
        announcementCreationLayout.setStatisticalOperations(result);
    }

    @Override
    public void clearValues() {
        announcementCreationLayout.clearValues();
    }
}
