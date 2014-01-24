package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDo2RestMapperV10Impl implements CommonDo2RestMapperV10 {

    @Autowired
    private ConfigurationService configurationService;

    private String               statisticalResourcesApiInternalEndpoint;
    private String               srmApiInternalEndpoint;
    private String               statisticalOperationsApiInternalEndpoint;
    private String               commonMetadataApiExternalEndpoint;

    private String               defaultLanguage;

    @PostConstruct
    public void init() throws Exception {

        // ENDPOINTS
        // Statistical operations internal Api (do not add api version! it is already stored in database (~latest))
        statisticalOperationsApiInternalEndpoint = configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
        statisticalOperationsApiInternalEndpoint = StringUtils.removeEnd(statisticalOperationsApiInternalEndpoint, "/");

        // SRM external Api (do not add api version! it is already stored in database (~latest))
        srmApiInternalEndpoint = configurationService.retrieveSrmInternalApiUrlBase();
        srmApiInternalEndpoint = StringUtils.removeEnd(srmApiInternalEndpoint, "/");

        // Statistical resources internal API (do not add api version! it is already stored in database (~latest))
        // FIXME: Cambiar internal por internal. Pendiente de que este la API Interna.
        statisticalResourcesApiInternalEndpoint = configurationService.retrieveStatisticalResourcesExternalApiUrlBase();
        statisticalResourcesApiInternalEndpoint = StringUtils.removeEnd(statisticalResourcesApiInternalEndpoint, "/");;

        // Common metadata external Api (do not add api version! it is already stored in database (~latest))
        commonMetadataApiExternalEndpoint = configurationService.retrieveCommonMetadataExternalApiUrlBase();
        commonMetadataApiExternalEndpoint = StringUtils.removeEnd(commonMetadataApiExternalEndpoint, "/");;

        // MISC
        defaultLanguage = configurationService.retrieveLanguageDefault();
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEM
    // ------------------------------------------------------------

    @Override
    public Resources externalItemEntityListToRest(List<ExternalItem> source) throws MetamacException {
        if (source.isEmpty()) {
            return null;
        }

        Resources result = new Resources();
        for (ExternalItem externalItem : source) {
            result.getResources().add(externalItemEntityToRest(externalItem));
        }
        result.setTotal(new BigInteger(String.valueOf(result.getResources().size())));
        return result;
    }

    @Override
    public Resource externalItemEntityToRest(ExternalItem source) throws MetamacException {
        Resource target = externalItemEntityToRestWithoutUrls(source);

        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemEntityToRest(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemEntityToRest(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(source.getType())) {
                target = statisticalOperationsExternalItemEntityToRest(source, target);
                // FIXME: Añadir tipos de external item del gpe
                // } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalResourcesApp(source.getType())) {
                // target = statisticalResourcesExternalItemEntityToRest(source, target);
            } else {
                throw new MetamacException(org.siemac.metamac.notifications.core.error.ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemEntityToRest");
            }
        }

        return target;
    }

    private Resource externalItemEntityToRestWithoutUrls(ExternalItem source) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setName(internationalStringEntityToRest(source.getTitle()));

        return target;
    }

    private Resource statisticalOperationsExternalItemEntityToRest(ExternalItem source, Resource target) {
        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(statisticalOperationsApiInternalEndpoint, source.getUri())));
        return target;
    }

    private Resource srmExternalItemEntityToRest(ExternalItem source, Resource target) {
        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(srmApiInternalEndpoint, source.getUri())));
        return target;
    }

    private Resource commonMetadataExternalItemEntityToRest(ExternalItem source, Resource target) {
        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(commonMetadataApiExternalEndpoint, source.getUri())));
        return target;
    }

    private Resource statisticalResourcesExternalItemEntityToRest(ExternalItem source, Resource target) {
        target.setSelfLink(uriToResourceLink(target.getKind(), RestUtils.createLink(statisticalResourcesApiInternalEndpoint, source.getUri())));
        return target;
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

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    @Override
    public InternationalString internationalStringEntityToRest(org.siemac.metamac.notifications.core.common.domain.InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.notifications.core.common.domain.LocalisedString source : sources.getTexts()) {
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