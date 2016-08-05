package org.siemac.metamac.notices.web.client;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.notices.core.constants.NoticesConstants;
import org.siemac.metamac.notices.web.client.gin.NoticesWebGinjector;
import org.siemac.metamac.notices.web.client.utils.AccessControlValues;
import org.siemac.metamac.notices.web.shared.GetAccessControlRolesAndAppsAction;
import org.siemac.metamac.notices.web.shared.GetAccessControlRolesAndAppsResult;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.web.common.client.MetamacSecurityEntryPoint;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NoticesWeb extends MetamacSecurityEntryPoint {

    private static Logger                   logger           = Logger.getLogger(NoticesWeb.class.getName());

    private static final boolean            SECURITY_ENABLED = true;

    private static MetamacPrincipal         principal;
    private static NoticesWebConstants      constants;
    private static NoticesWebMessages       messages;
    private static NoticesWebCoreMessages   coreMessages;

    public static final NoticesWebGinjector ginjector        = GWT.create(NoticesWebGinjector.class);

    @Override
    public void onModuleLoad() {
        setUncaughtExceptionHandler();

        prepareApplication(SECURITY_ENABLED);
    }

    @Override
    protected void onBeforeLoadApplication() {

        ginjector.getDispatcher().execute(new GetAccessControlRolesAndAppsAction(), new AsyncCallback<GetAccessControlRolesAndAppsResult>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error retrieving access control values (roles and apps): " + caught.getMessage());
                loadApplication();
            }
            @Override
            public void onSuccess(GetAccessControlRolesAndAppsResult result) {
                AccessControlValues.setRoles(result.getRoles());
                AccessControlValues.setApps(result.getApps());
                loadApplication();
            }
        });
    }

    @Override
    protected String[] getPropertiesToLoad() {
        return new String[]{};
    }

    @Override
    protected void setConfigurationProperties(Map<String, String> propertyValues) {
        super.setConfigurationProperties(propertyValues);
    }

    public static MetamacPrincipal getCurrentUser() {
        return NoticesWeb.principal;
    }

    public static NoticesWebConstants getConstants() {
        if (constants == null) {
            constants = (NoticesWebConstants) GWT.create(NoticesWebConstants.class);
        }
        return constants;
    }

    public static NoticesWebMessages getMessages() {
        if (messages == null) {
            messages = (NoticesWebMessages) GWT.create(NoticesWebMessages.class);
        }
        return messages;
    }

    public static NoticesWebCoreMessages getCoreMessages() {
        if (coreMessages == null) {
            coreMessages = (NoticesWebCoreMessages) GWT.create(NoticesWebCoreMessages.class);
        }
        return coreMessages;
    }

    public static NoticesWebGinjector getResourcesWebGinjector() {
        return ginjector;
    }

    public static void showErrorPage() {
        ginjector.getPlaceManager().revealErrorPlace(null);
    }

    @Override
    protected String getApplicationTitle() {
        return getConstants().appTitle();
    }

    @Override
    protected MetamacPrincipal getPrincipal() {
        return principal;
    }

    @Override
    protected void setPrincipal(MetamacPrincipal principal) {
        NoticesWeb.principal = principal;
    }

    @Override
    protected String getSecurityApplicationId() {
        return NoticesConstants.SECURITY_APPLICATION_ID;
    }

    @Override
    protected MetamacWebGinjector getWebGinjector() {
        return getResourcesWebGinjector();
    }

    @Override
    protected String getBundleName() {
        return "messages-notifications-web";
    }
}
