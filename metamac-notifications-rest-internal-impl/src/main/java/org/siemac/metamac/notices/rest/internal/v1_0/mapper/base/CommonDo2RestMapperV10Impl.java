package org.siemac.metamac.notices.rest.internal.v1_0.mapper.base;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.notices.core.conf.NoticesConfigurationService;
import org.siemac.metamac.notices.rest.internal.constants.NoticesRestConstants;
import org.siemac.metamac.notices.rest.internal.invocation.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourcesInternal;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CommonDo2RestMapperV10Impl implements CommonDo2RestMapperV10 {

    @Autowired
    private NoticesConfigurationService             configurationService;

    @Autowired
    @Qualifier("statisticalOperationsInternalFacade")
    private StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    private String                                  noticesApiInternalEndpointV10;

    private String                                  statisticalResourcesApiInternalEndpoint;
    private String                                  srmApiInternalEndpoint;
    private String                                  statisticalOperationsApiInternalEndpoint;
    private String                                  commonMetadataApiExternalEndpoint;

    private String                                  statisticalResourcesInternalWebApplication;
    private String                                  srmInternalWebApplication;
    private String                                  statisticalOperationsInternalWebApplication;
    private String                                  commonMetadataInternalWebApplication;

    private String                                  defaultLanguage;

    @PostConstruct
    public void init() throws Exception {
        initApiEndpoints();
        initInternalWebApplicationsUrls();
        initOtherProperties();

    }

    private void initInternalWebApplicationsUrls() throws MetamacException {
        // Statistical operations
        statisticalOperationsInternalWebApplication = configurationService.retrieveStatisticalOperationsInternalWebApplicationUrlBase();
        statisticalOperationsInternalWebApplication = StringUtils.removeEnd(statisticalOperationsInternalWebApplication, "/");

        // SRM
        srmInternalWebApplication = configurationService.retrieveSrmInternalWebApplicationUrlBase();
        srmInternalWebApplication = StringUtils.removeEnd(srmInternalWebApplication, "/");

        // Statistical resources
        statisticalResourcesInternalWebApplication = configurationService.retrieveStatisticalResourcesInternalWebApplicationUrlBase();
        statisticalResourcesInternalWebApplication = StringUtils.removeEnd(statisticalResourcesInternalWebApplication, "/");

        // Common metadata
        commonMetadataInternalWebApplication = configurationService.retrieveCommonMetadataInternalWebApplicationUrlBase();
        commonMetadataInternalWebApplication = StringUtils.removeEnd(commonMetadataInternalWebApplication, "/");

    }

    private void initApiEndpoints() throws MetamacException {
        // Notices internal API v1.0
        String noticesApiInternalEndpoint = configurationService.retrieveNoticesInternalApiUrlBase();
        noticesApiInternalEndpointV10 = RestUtils.createLink(noticesApiInternalEndpoint, NoticesRestConstants.API_VERSION_1_0);

        // Statistical operations internal Api (do not add api version! it is already stored in database (~latest))
        statisticalOperationsApiInternalEndpoint = configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
        statisticalOperationsApiInternalEndpoint = StringUtils.removeEnd(statisticalOperationsApiInternalEndpoint, "/");

        // SRM external Api (do not add api version! it is already stored in database (~latest))
        srmApiInternalEndpoint = configurationService.retrieveSrmInternalApiUrlBase();
        srmApiInternalEndpoint = StringUtils.removeEnd(srmApiInternalEndpoint, "/");

        // Statistical resources internal API (do not add api version! it is already stored in database (~latest))
        statisticalResourcesApiInternalEndpoint = configurationService.retrieveStatisticalResourcesInternalApiUrlBase();
        statisticalResourcesApiInternalEndpoint = StringUtils.removeEnd(statisticalResourcesApiInternalEndpoint, "/");

        // Common metadata external Api (do not add api version! it is already stored in database (~latest))
        commonMetadataApiExternalEndpoint = configurationService.retrieveCommonMetadataExternalApiUrlBase();
        commonMetadataApiExternalEndpoint = StringUtils.removeEnd(commonMetadataApiExternalEndpoint, "/");
    }

    private void initOtherProperties() throws MetamacException {
        defaultLanguage = configurationService.retrieveLanguageDefault();
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEM
    // ------------------------------------------------------------

    @Override
    public ResourcesInternal externalItemEntityListToRest(List<ExternalItem> source) throws MetamacException {
        if (source.isEmpty()) {
            return null;
        }

        ResourcesInternal result = new ResourcesInternal();
        for (ExternalItem externalItem : source) {
            result.getResources().add(externalItemEntityToRest(externalItem));
        }
        result.setTotal(new BigInteger(String.valueOf(result.getResources().size())));
        return result;
    }

    @Override
    public ResourceInternal externalItemEntityToRest(ExternalItem source) throws MetamacException {
        ResourceInternal target = externalItemEntityToRestWithoutUrls(source);

        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemEntityToRest(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemEntityToRest(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(source.getType())) {
                target = statisticalOperationsExternalItemEntityToRest(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalResourcesApp(source.getType())) {
                target = statisticalResourcesExternalItemEntityToRest(source, target);
            } else {
                throw new MetamacException(org.siemac.metamac.notices.core.error.ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemEntityToRest");
            }
        }

        return target;
    }

    private ResourceInternal externalItemEntityToRestWithoutUrls(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setName(internationalStringEntityToRest(source.getTitle()));

        return target;
    }

    private ResourceInternal statisticalOperationsExternalItemEntityToRest(ExternalItem source, ResourceInternal target) {
        // The title of the statistical operation may not be updated
        target.setName(getUpdatedStatisticalOperationName(source.getCode()));

        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(statisticalOperationsApiInternalEndpoint, source.getUri())));
        target.setManagementAppLink(RestUtils.createLink(statisticalOperationsInternalWebApplication, source.getManagementAppUrl()));
        return target;
    }

    private ResourceInternal srmExternalItemEntityToRest(ExternalItem source, ResourceInternal target) {
        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(srmApiInternalEndpoint, source.getUri())));
        target.setManagementAppLink(RestUtils.createLink(srmInternalWebApplication, source.getManagementAppUrl()));
        return target;
    }

    private ResourceInternal commonMetadataExternalItemEntityToRest(ExternalItem source, ResourceInternal target) {
        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(commonMetadataApiExternalEndpoint, source.getUri())));
        target.setManagementAppLink(RestUtils.createLink(commonMetadataInternalWebApplication, source.getManagementAppUrl()));
        return target;
    }

    private ResourceInternal statisticalResourcesExternalItemEntityToRest(ExternalItem source, ResourceInternal target) {
        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(statisticalResourcesApiInternalEndpoint, source.getUri())));
        target.setManagementAppLink(RestUtils.createLink(statisticalResourcesInternalWebApplication, source.getManagementAppUrl()));
        return target;
    }

    private InternationalString getUpdatedStatisticalOperationName(String operationCode) {
        Operation operation = statisticalOperationsRestInternalFacade.retrieveOperation(operationCode);
        return operation.getName();
    }

    // ------------------------------------------------------------
    // RESOURCE LINKS
    // ------------------------------------------------------------

    @Override
    public ResourceLink uriToResourceLink(String kind, String href) {
        ResourceLink target = new ResourceLink();
        target.setKind(kind);
        target.setHref(href);
        return target;
    }

    // API/[ARTEFACT_TYPE]
    // API/[ARTEFACT_TYPE]/{code}
    @Override
    public String toResourceLink(String resourceSubpath, String code) {
        String link = RestUtils.createLink(noticesApiInternalEndpointV10, resourceSubpath);

        if (StringUtils.isNotBlank(code)) {
            link = RestUtils.createLink(link, code);
        }
        return link;
    }

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    @Override
    public InternationalString internationalStringEntityToRest(org.siemac.metamac.notices.core.common.domain.InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.notices.core.common.domain.LocalisedString source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setLang(source.getLocale());
            target.setValue(source.getLabel());
            targets.getTexts().add(target);
        }
        return targets;
    }

    @Override
    public InternationalString internationalStringEntityToRest(InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (LocalisedString source : sources.getTexts()) {
            targets.getTexts().add(source);
        }
        return targets;
    }

    @Override
    public InternationalString stringEntityToInternationalStringRest(String source) {
        if (source == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        LocalisedString target = new LocalisedString();
        target.setLang(defaultLanguage);
        target.setValue(source);
        targets.getTexts().add(target);
        return targets;
    }

}