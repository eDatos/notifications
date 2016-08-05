package org.siemac.metamac.notices.web.server.rest;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.notices.web.shared.dto.AppDto;
import org.siemac.metamac.notices.web.shared.dto.RoleDto;
import org.siemac.metamac.rest.access_control.v1_0.domain.App;
import org.siemac.metamac.rest.access_control.v1_0.domain.Apps;
import org.siemac.metamac.rest.access_control.v1_0.domain.Role;
import org.siemac.metamac.rest.access_control.v1_0.domain.Roles;
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

    @Override
    public List<AppDto> buildAppDtos(Apps sources) throws MetamacWebException {
        List<AppDto> targets = new ArrayList<AppDto>(sources.getApps().size());
        for (App source : sources.getApps()) {
            targets.add(buildAppDto(source));
        }
        return targets;
    }

    @Override
    public List<RoleDto> buildRoleDtos(Roles sources) throws MetamacWebException {
        List<RoleDto> targets = new ArrayList<RoleDto>(sources.getRoles().size());
        for (Role source : sources.getRoles()) {
            targets.add(buildRoleDto(source));
        }
        return targets;
    }

    private AppDto buildAppDto(App source) {
        AppDto target = new AppDto();
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        return target;
    }

    private RoleDto buildRoleDto(Role source) {
        RoleDto target = new RoleDto();
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        return target;
    }
}
