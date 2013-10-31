package org.siemac.metamac.notifications.rest.invocation;

import javax.annotation.PostConstruct;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metamacApisLocatorRestExternal")
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService configurationService;

    // FIXME SRM: cambiar a API externa
    // private SrmRestInternalFacadeV10 srmRestExternalFacadeV10 = null;

    @PostConstruct
    public void initService() throws Exception {

        // String srmExternalApi = configurationService.retrieveSrmExternalApiUrlBase();
        // srmRestExternalFacadeV10 = JAXRSClientFactory.create(srmExternalApi, SrmRestInternalFacadeV10.class, null, true); // true to do thread safe
    }

    // public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
    // // reset thread context
    // WebClient.client(commonMetadataRestExternalFacadeV10).reset();
    // WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");
    //
    // return commonMetadataRestExternalFacadeV10;
    // }
}
