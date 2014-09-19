package org.siemac.metamac.notices.web.server;

import org.siemac.metamac.notices.web.server.handlers.CreateNoticeActionHandler;
import org.siemac.metamac.notices.web.server.handlers.GetAccessControlRolesAndAppsActionHandler;
import org.siemac.metamac.notices.web.server.handlers.GetNoticeActionHandler;
import org.siemac.metamac.notices.web.server.handlers.GetNoticesActionHandler;
import org.siemac.metamac.notices.web.server.handlers.GetStatisticalOperationsActionHandler;
import org.siemac.metamac.notices.web.server.handlers.GetUserGuideUrlActionHandler;
import org.siemac.metamac.notices.web.server.handlers.UpdateNoticeRecieverAcknowledgeActionHandler;
import org.siemac.metamac.notices.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.notices.web.shared.CreateNoticeAction;
import org.siemac.metamac.notices.web.shared.GetAccessControlRolesAndAppsAction;
import org.siemac.metamac.notices.web.shared.GetNoticeAction;
import org.siemac.metamac.notices.web.shared.GetNoticesAction;
import org.siemac.metamac.notices.web.shared.GetStatisticalOperationsAction;
import org.siemac.metamac.notices.web.shared.GetUserGuideUrlAction;
import org.siemac.metamac.notices.web.shared.UpdateNoticeRecieverAcknowledgeAction;
import org.siemac.metamac.web.common.server.handlers.CloseSessionActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetLoginPageUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetNavigationBarUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.LoadConfigurationPropertiesActionHandler;
import org.siemac.metamac.web.common.server.handlers.MockCASUserActionHandler;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesAction;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.ValidateTicketAction;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.spring.HandlerModule;

/**
 * Module which binds the handlers and configurations.
 */
@Component
public class ServerModule extends HandlerModule {

    public ServerModule() {
    }

    @Override
    protected void configureHandlers() {
        bindHandler(GetNoticesAction.class, GetNoticesActionHandler.class);
        bindHandler(GetNoticeAction.class, GetNoticeActionHandler.class);
        bindHandler(UpdateNoticeRecieverAcknowledgeAction.class, UpdateNoticeRecieverAcknowledgeActionHandler.class);
        bindHandler(CreateNoticeAction.class, CreateNoticeActionHandler.class);
        bindHandler(GetStatisticalOperationsAction.class, GetStatisticalOperationsActionHandler.class);
        bindHandler(GetAccessControlRolesAndAppsAction.class, GetAccessControlRolesAndAppsActionHandler.class);

        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(LoadConfigurationPropertiesAction.class, LoadConfigurationPropertiesActionHandler.class);
        bindHandler(GetUserGuideUrlAction.class, GetUserGuideUrlActionHandler.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }
}
