package org.siemac.metamac.notices.web.client.widgets;

import java.util.List;

import org.siemac.metamac.notices.web.client.utils.AccessControlValues;

public class SearchAppsItem extends SearchAccessControlValueItem {

    public SearchAppsItem(String name, String title) {
        super(name, title);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List getAccessControlValueDtos() {
        return AccessControlValues.getApps();
    }
}
