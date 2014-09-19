package org.siemac.metamac.notices.web.server.rest;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.stereotype.Component;

@Component
public class RestMapperImpl implements RestMapper {

    @Override
    public ExternalItemDto buildExternalItemDtoFromOperation(ResourceInternal resourceInternal) throws MetamacWebException {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resourceInternal.getId());
        externalItemDto.setCodeNested(resourceInternal.getNestedId());
        externalItemDto.setUri(resourceInternal.getSelfLink().getHref());
        externalItemDto.setUrn(resourceInternal.getUrn());
        externalItemDto.setType(TypeExternalArtefactsEnum.fromValue(resourceInternal.getKind()));
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resourceInternal.getName()));
        externalItemDto.setManagementAppUrl(resourceInternal.getManagementAppLink());
        return externalItemDto;
    }

    @Override
    public List<ExternalItemDto> buildExternalItemDtosFromOperations(List<ResourceInternal> operations) throws MetamacWebException {
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>(operations.size());
        for (ResourceInternal resourceInternal : operations) {
            externalItemDtos.add(buildExternalItemDtoFromOperation(resourceInternal));
        }
        return externalItemDtos;
    }
}
