package org.siemac.metamac.notifications.rest.v1_0.mapper.base;

import java.util.List;

import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;

public interface CommonDo2RestMapperV10 {

    public Resources toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages);

    public Resource toResourceExternalItemStatisticalOperations(ExternalItem source, List<String> selectedLanguages);

}
