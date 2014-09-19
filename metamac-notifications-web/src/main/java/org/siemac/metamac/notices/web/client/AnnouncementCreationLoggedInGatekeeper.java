package org.siemac.metamac.notices.web.client;

import static org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum.ADMINISTRADOR;
import static org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum.REDACTOR_AVISOS;

import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.notices.core.notice.enume.domain.NoticesRoleEnum;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.LoggedInGatekeeper;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

public class AnnouncementCreationLoggedInGatekeeper extends LoggedInGatekeeper {

    private final NoticesRoleEnum[] allowedRoles = new NoticesRoleEnum[]{ADMINISTRADOR, REDACTOR_AVISOS};

    @Inject
    public AnnouncementCreationLoggedInGatekeeper(EventBus eventBus) {
        super(eventBus);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Enum[] getAllowedRoles() {
        return allowedRoles;
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
