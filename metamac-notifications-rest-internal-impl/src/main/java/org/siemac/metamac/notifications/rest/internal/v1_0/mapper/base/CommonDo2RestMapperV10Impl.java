package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.utils.RestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonDo2RestMapperV10Impl implements CommonDo2RestMapperV10 {

    private static final Logger  logger = LoggerFactory.getLogger(CommonDo2RestMapperV10.class);

    @Autowired
    private ConfigurationService configurationService;

    private String               statisticalOperationsApiInternalEndpoint;

    @PostConstruct
    public void init() throws Exception {

        // ENDPOINTS
        // Statistical operations internal Api (do not add api version! it is already stored in database (~latest))
        statisticalOperationsApiInternalEndpoint = configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
        statisticalOperationsApiInternalEndpoint = StringUtils.removeEnd(statisticalOperationsApiInternalEndpoint, "/");
    }

    @Override
    public Resources toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Resources targets = new Resources();
        for (ExternalItem source : sources) {
            Resource target = toResourceExternalItemStatisticalOperations(source, selectedLanguages);
            targets.getResources().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    @Override
    public Resource toResourceExternalItemStatisticalOperations(ExternalItem source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        toResourceExternalItem(source, statisticalOperationsApiInternalEndpoint, target, selectedLanguages);
        return target;
    }

    private void toResourceExternalItem(ExternalItem source, String apiExternalItemBase, Resource target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItemBase, source.getUri())));
        target.setName(toInternationalString(source.getTitle(), selectedLanguages));
    }

    private ResourceLink toResourceLink(String kind, String href) {
        ResourceLink target = new ResourceLink();
        target.setKind(kind);
        target.setHref(href);
        return target;
    }

    public InternationalString toInternationalString(org.siemac.metamac.notifications.core.common.domain.InternationalString sources, List<String> selectedLanguages) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.notifications.core.common.domain.LocalisedString source : sources.getTexts()) {
            if (selectedLanguages.isEmpty() || selectedLanguages.contains(source.getLocale())) {
                LocalisedString target = new LocalisedString();
                target.setLang(source.getLocale());
                target.setValue(source.getLabel());
                targets.getTexts().add(target);
            }
        }
        return targets;
    }
}