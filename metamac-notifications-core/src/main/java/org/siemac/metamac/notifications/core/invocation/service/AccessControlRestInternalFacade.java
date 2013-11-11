package org.siemac.metamac.notifications.core.invocation.service;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.access_control.v1_0.domain.User;

public interface AccessControlRestInternalFacade {

    public List<User> findUsers(String query) throws MetamacException;
}