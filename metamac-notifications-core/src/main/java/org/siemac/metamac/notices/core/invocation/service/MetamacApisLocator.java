package org.siemac.metamac.notices.core.invocation.service;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.access_control.rest.internal.v1_0.service.AccessControlRestInternalFacadeV1_0;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metamacApisLocatorRestExternal")
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService                configurationService;

    private AccessControlRestInternalFacadeV1_0 accessControlRestInternalFacadeV1_0 = null;

    @PostConstruct
    public void initService() throws Exception {
        String srmExternalApi = configurationService.retrieveAccessControlInternalApiUrlBase();
        accessControlRestInternalFacadeV1_0 = JAXRSClientFactory.create(srmExternalApi, AccessControlRestInternalFacadeV1_0.class, null, true); // true to do thread safe
    }

    public AccessControlRestInternalFacadeV1_0 getAccessControlRestInternalFacadeV1_0() {
        // reset thread context
        WebClient.client(accessControlRestInternalFacadeV1_0).reset();
        WebClient.client(accessControlRestInternalFacadeV1_0).accept("application/xml");
        return accessControlRestInternalFacadeV1_0;
    }

}
