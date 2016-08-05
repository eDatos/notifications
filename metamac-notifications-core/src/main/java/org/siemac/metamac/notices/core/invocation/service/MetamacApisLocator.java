package org.siemac.metamac.notices.core.invocation.service;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.access_control.rest.internal.v1_0.service.AccessControlRestInternalFacadeV1_0;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.StatisticalOperationsRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metamacApisLocatorRestExternal")
public class MetamacApisLocator {

    @Autowired
    private NoticesConfigurationService                configurationService;

    private AccessControlRestInternalFacadeV1_0        accessControlRestInternalFacadeV1_0         = null;
    private StatisticalOperationsRestInternalFacadeV10 statisticalOperationsRestInternalFacadeV1_0 = null;

    @PostConstruct
    public void initService() throws Exception {
        String accessControlInternalApi = configurationService.retrieveAccessControlInternalApiUrlBase();
        String statisticalOperationsInternalApi = configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
        accessControlRestInternalFacadeV1_0 = JAXRSClientFactory.create(accessControlInternalApi, AccessControlRestInternalFacadeV1_0.class, null, true); // true to do thread safe
        statisticalOperationsRestInternalFacadeV1_0 = JAXRSClientFactory.create(statisticalOperationsInternalApi, StatisticalOperationsRestInternalFacadeV10.class, null, true);
    }

    public AccessControlRestInternalFacadeV1_0 getAccessControlRestInternalFacadeV1_0() {
        // reset thread context
        WebClient.client(accessControlRestInternalFacadeV1_0).reset();
        WebClient.client(accessControlRestInternalFacadeV1_0).accept("application/xml");
        return accessControlRestInternalFacadeV1_0;
    }

    public StatisticalOperationsRestInternalFacadeV10 getStatisticalOperationsRestInternalFacadeV10() {
        // reset thread context
        WebClient.client(statisticalOperationsRestInternalFacadeV1_0).reset();
        WebClient.client(statisticalOperationsRestInternalFacadeV1_0).accept("application/xml");

        return statisticalOperationsRestInternalFacadeV1_0;
    }
}
