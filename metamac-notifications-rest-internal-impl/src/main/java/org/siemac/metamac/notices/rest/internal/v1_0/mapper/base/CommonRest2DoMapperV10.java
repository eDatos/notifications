package org.siemac.metamac.notices.rest.internal.v1_0.mapper.base;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notices.core.common.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;

public interface CommonRest2DoMapperV10 {

    public ExternalItem externalItemRestStatisticalOperationToExternalItemDo(Resource source, ExternalItem target) throws MetamacException;

}
