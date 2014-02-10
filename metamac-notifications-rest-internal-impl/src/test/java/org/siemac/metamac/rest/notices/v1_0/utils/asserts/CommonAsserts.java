package org.siemac.metamac.rest.notices.v1_0.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.core.common.constants.CoreCommonConstants.API_LATEST;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.common.test.constants.ConfigurationMockConstants;
import org.siemac.metamac.core.common.constants.shared.RegularExpressionConstants;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.common.domain.InternationalString;
import org.siemac.metamac.notices.core.common.domain.LocalisedString;
import org.siemac.metamac.notices.core.error.ServiceExceptionType;
import org.siemac.metamac.notices.rest.internal.domain.utils.ResourceInternalUtils;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourcesInternal;

public class CommonAsserts extends MetamacRestAsserts {

    public static void assertEqualsExternalItemCollection(java.util.List<ExternalItem> doCollection, ResourcesInternal restCollection) throws MetamacException {
        if (doCollection.isEmpty() && (restCollection == null || restCollection.getResources().isEmpty())) {
            return;
        }

        assertEquals(doCollection.size(), restCollection.getTotal().intValue());

        for (ExternalItem doResource : doCollection) {
            boolean found = false;
            for (org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal restResource : restCollection.getResources()) {
                if (doResource.getCode().equals(restResource.getId())) {
                    found = true;
                    assertEqualsExternalItem(doResource, restResource);
                    return;
                }
            }

            if (!found) {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Not equals resources collection. Not found: " + doResource.getCode());
            }
        }
    }

    public static void assertEqualsExternalItem(ExternalItem doResource, ResourceInternal restResource) {

        TypeExternalArtefactsEnum typeExternalArtefact = TypeExternalArtefactsEnum.fromValue(restResource.getKind());
        String baseApi = null;
        String baseWebApplication = null;

        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(typeExternalArtefact)) {
            baseWebApplication = ConfigurationMockConstants.COMMON_METADATA_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.COMMON_METADATA_EXTERNAL_API_URL_BASE;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(typeExternalArtefact)) {
            baseWebApplication = ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.STATISTICAL_OPERATIONS_INTERNAL_API_URL_BASE;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(typeExternalArtefact)) {
            baseWebApplication = ConfigurationMockConstants.SRM_INTERNAL_WEB_APP_URL_BASE;
            baseApi = ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE;
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalResourcesApp(typeExternalArtefact)) {
            baseWebApplication = ConfigurationMockConstants.STATISTICAL_RESOURCES_INTERNAL_API_URL_BASE;
            baseApi = ConfigurationMockConstants.STATISTICAL_RESOURCES_INTERNAL_WEB_APP_URL_BASE;
        } else {
            fail("unexpected type of external item");
        }

        assertEqualsExternalItem(doResource, restResource, baseApi, baseWebApplication);
    }

    public static void assertEqualsExternalItem(ExternalItem doResource, ResourceInternal restResource, String baseApi, String baseWebApplication) {
        assertEquals(doResource.getCode(), restResource.getId());
        assertEquals(doResource.getCodeNested(), restResource.getNestedId());
        assertEquals(doResource.getUrn(), restResource.getUrn());
        assertEquals(doResource.getUrnProvider(), restResource.getUrnProvider());
        assertEquals(doResource.getType(), TypeExternalArtefactsEnum.fromValue(restResource.getKind()));
        assertEqualsInternationalString(doResource.getTitle(), restResource.getName());

        String expectedDoUri = restResource.getSelfLink().getHref().replaceFirst(baseApi, StringUtils.EMPTY);
        expectedDoUri = expectedDoUri.replaceFirst(RegularExpressionConstants.API_VERSION_REG_EXP, API_LATEST);
        assertEquals(expectedDoUri, doResource.getUri());

        String expectedDoWebApp = restResource.getManagementAppLink().replaceFirst(baseWebApplication, StringUtils.EMPTY);
        assertEquals(expectedDoWebApp, doResource.getManagementAppUrl());
    }

    public static void assertEqualsInternationalString(InternationalString entity, org.siemac.metamac.rest.common.v1_0.domain.InternationalString rest) {
        assertEqualsNullability(entity, rest);
        if (entity == null) {
            return;
        }

        assertEquals(entity.getTexts().size(), rest.getTexts().size());
        for (LocalisedString localisedStringExpected : entity.getTexts()) {
            assertEquals(localisedStringExpected.getLabel(), ResourceInternalUtils.getLocalisedLabel(rest, localisedStringExpected.getLocale()));
        }
    }
}
