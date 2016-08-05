package org.siemac.metamac.notices.web.client;

import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.LoggedInGatekeeper;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class NoticesLoggedInGatekeeper extends LoggedInGatekeeper {

    @Inject
    public NoticesLoggedInGatekeeper(EventBus eventBus) {
        super(eventBus);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Enum[] getAllowedRoles() {
        return NoticesRoleEnum.values();
    }

    @Override
    public String getApplicationId() {
        return NoticesConstants.SECURITY_APPLICATION_ID;
    }

    @Override
    public MetamacPrincipal getCurrentUser() {
        return NoticesWeb.getCurrentUser();
    }
}
