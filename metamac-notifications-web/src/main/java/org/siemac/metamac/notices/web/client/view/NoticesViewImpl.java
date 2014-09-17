package org.siemac.metamac.notices.web.client.view;

import org.siemac.metamac.notices.web.client.presenter.NoticesPresenter;
import org.siemac.metamac.notices.web.client.view.handlers.NoticesUiHandlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.VLayout;

public class NoticesViewImpl extends ViewWithUiHandlers<NoticesUiHandlers> implements NoticesPresenter.NoticesView {

    private VLayout panel;

    @Inject
    public NoticesViewImpl() {
        super();
        panel = new VLayout();
        panel.setHeight100();
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
