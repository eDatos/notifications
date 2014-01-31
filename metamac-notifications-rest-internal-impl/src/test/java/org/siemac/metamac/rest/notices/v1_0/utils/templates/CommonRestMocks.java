package org.siemac.metamac.rest.notices.v1_0.utils.templates;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.test.utils.MetamacRestMocks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.service.NoticesRestInternalFacadeV10BaseTest;

public class CommonRestMocks {

    public static ResourceInternal mockResourceFromExternalItemSrm(String id, String sampleResourceSubpath, String kind) {
        return mockResourceFromExternalItem(id, NoticesRestInternalFacadeV10BaseTest.srmApiInternalEndpoint, NoticesRestInternalFacadeV10BaseTest.srmInternalWebApplication, sampleResourceSubpath,
                kind);
    }

    public static ResourceInternal mockResourceFromExternalItem(String id, String endpointApi, String managementApp, String sampleResourceSubpath, String kind) {
        ResourceInternal resource = new ResourceInternal();
        resource.setId(id);
        resource.setUrn("urn:" + id);
        resource.setKind(kind);
        resource.setSelfLink(MetamacRestMocks.mockResourceLink(kind, endpointApi + "/" + sampleResourceSubpath + "/" + id));
        resource.setManagementAppLink(managementApp + "/" + sampleResourceSubpath + "/" + id);
        resource.setName(mockInternationalString("es", id + " en Español"));
        if (TypeExternalArtefactsEnum.AGENCY.getValue().equals(kind) || TypeExternalArtefactsEnum.CATEGORY.getValue().equals(kind)) {
            resource.setNestedId(id + "Nested");
        }
        return resource;
    }

    public static InternationalString mockInternationalString(String locale, String label) {
        // Note: only one locale, because it is a set and change orden in xml responses, so only there are svn differences
        InternationalString internationalString = new InternationalString();
        LocalisedString internationalStringLocale1 = new LocalisedString();
        internationalStringLocale1.setLang(locale);
        internationalStringLocale1.setValue(label);
        internationalString.getTexts().add(internationalStringLocale1);
        return internationalString;
    }
}