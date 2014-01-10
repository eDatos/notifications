package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.notifications.core.common.domain.ExternalItemRepository;
import org.siemac.metamac.notifications.core.common.domain.InternationalString;
import org.siemac.metamac.notifications.core.common.domain.InternationalStringRepository;
import org.siemac.metamac.notifications.core.common.domain.LocalisedString;
import org.siemac.metamac.notifications.core.error.ServiceExceptionType;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonRest2DoMapperV10Impl implements CommonRest2DoMapperV10 {

    @Autowired
    private ConfigurationService          configurationService;

    @Autowired
    private ExternalItemRepository        externalItemRepository;

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    @Override
    public ExternalItem externalItemRestStatisticalOperationToExternalItemDo(Resource source, ExternalItem target) throws MetamacException {

        target = externalItemDtoToExternalItem(source, target);
        if (target != null) {
            target.setUri(CoreCommonUtil.externalItemUrlDtoToUrlDo(getStatisticalOperationsInternalApiUrlBase(), target.getUri()));
            target.setManagementAppUrl(CoreCommonUtil.externalItemUrlDtoToUrlDo(getStatisticalOperationsInternalWebApplicationUrlBase(), target.getManagementAppUrl()));
        }
        return target;
    }

    private String getStatisticalOperationsInternalWebApplicationUrlBase() throws MetamacException {
        return configurationService.retrieveStatisticalOperationsInternalWebApplicationUrlBase();
    }

    private String getStatisticalOperationsInternalApiUrlBase() throws MetamacException {
        return configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
    }

    public ExternalItem externalItemDtoToExternalItem(Resource source, ExternalItem target) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                externalItemRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            // New
            target = new ExternalItem();
        }
        target.setCode(source.getId());
        target.setCodeNested(source.getNestedId());
        // target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        // target.setUrnProvider(source.getUrnProvider());
        target.setType(TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        // target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringToEntity(source.getName(), target.getTitle(), "metadataName + ServiceExceptionParametersInternal.EXTERNAL_ITEM_TITLE"));

        return target;
    }

    public InternationalString internationalStringToEntity(org.siemac.metamac.rest.common.v1_0.domain.InternationalString source, InternationalString target, String metadataName)
            throws MetamacException {

        // Check it is valid
        checkInternationalStringDtoValid(source, metadataName);

        // Transform
        if (source == null) {
            if (target != null) {
                // Delete old entity
                internationalStringRepository.delete(target);
            }
            return null;
        }

        if (ValidationUtils.isEmpty(source)) {
            // international string is not complete
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
        }

        if (target == null) {
            target = new InternationalString();
        }
        Set<LocalisedString> localisedStringEntities = localisedStringDtoToDo(source.getTexts(), target.getTexts(), target);
        target.getTexts().clear();
        target.getTexts().addAll(localisedStringEntities);

        return target;
    }

    private Set<LocalisedString> localisedStringDtoToDo(List<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString> sources, Set<LocalisedString> targets,
            InternationalString internationalStringTarget) {

        Set<LocalisedString> targetsBefore = targets;
        targets = new HashSet<LocalisedString>();

        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString source : sources) {
            boolean existsBefore = false;
            for (LocalisedString target : targetsBefore) {
                if (source.getLang().equals(target.getLocale())) {
                    targets.add(localisedStringDtoToDo(source, target, internationalStringTarget));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                targets.add(localisedStringDtoToDo(source, internationalStringTarget));
            }
        }
        return targets;
    }

    private LocalisedString localisedStringDtoToDo(org.siemac.metamac.rest.common.v1_0.domain.LocalisedString source, InternationalString internationalStringTarget) {
        LocalisedString target = new LocalisedString();
        localisedStringDtoToDo(source, target, internationalStringTarget);
        return target;
    }

    private LocalisedString localisedStringDtoToDo(org.siemac.metamac.rest.common.v1_0.domain.LocalisedString source, LocalisedString target, InternationalString internationalStringTarget) {
        target.setLabel(source.getValue());
        target.setLocale(source.getLang());
        target.setInternationalString(internationalStringTarget);
        return target;
    }

    private void checkInternationalStringDtoValid(org.siemac.metamac.rest.common.v1_0.domain.InternationalString source, String metadataName) throws MetamacException {
        if (source == null) {
            return;
        }
        if (ValidationUtils.isEmpty(source)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(CommonServiceExceptionType.METADATA_REQUIRED).withMessageParameters(metadataName).build();
        }
        checkInternationalStringDtoLength(source, metadataName);
        checkInternationalStringDtoTranslations(source, metadataName);
    }

    private void checkInternationalStringDtoTranslations(org.siemac.metamac.rest.common.v1_0.domain.InternationalString source, String metadataName) throws MetamacException {
        if (source == null) {
            return;
        }
        String locale = configurationService.retrieveLanguageDefault();
        if (locale == null) {
            return;
        }

        boolean foundLocale = false;
        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString localisedString : source.getTexts()) {
            if (locale.toUpperCase().equals(localisedString.getLang().toUpperCase())) {
                foundLocale = true;
                break;
            }
        }

        if (!foundLocale) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(CommonServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE).withMessageParameters(metadataName).build();
        }
    }

    private void checkInternationalStringDtoLength(org.siemac.metamac.rest.common.v1_0.domain.InternationalString source, String metadataName) throws MetamacException {
        if (source == null) {
            return;
        }
        int maximumLength = CoreCommonConstants.LOCALISED_STRING_MAXIMUM_LENGTH;
        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString localisedString : source.getTexts()) {
            if (localisedString.getValue() != null && localisedString.getValue().length() > maximumLength) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(CommonServiceExceptionType.METADATA_MAXIMUM_LENGTH).withMessageParameters(metadataName, String.valueOf(maximumLength))
                        .build();
            }
        }
    }
}