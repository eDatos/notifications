package org.siemac.metamac.notices.web.client.view.handlers;

import com.gwtplatform.mvp.client.UiHandlers;

public interface MainPageUiHandlers extends UiHandlers {

    void closeSession();

    void onNavigationPaneSectionHeaderClicked(String name);
    void onNavigationPaneSectionClicked(String name);

    void openHelpUrl();
}
