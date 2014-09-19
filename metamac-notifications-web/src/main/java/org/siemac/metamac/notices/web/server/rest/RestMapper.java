package org.siemac.metamac.notices.web.server.rest;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface RestMapper {

    public ExternalItemDto buildExternalItemDtoFromOperation(ResourceInternal operation) throws MetamacWebException;
    public List<ExternalItemDto> buildExternalItemDtosFromOperations(List<ResourceInternal> operations) throws MetamacWebException;
}
