package org.siemac.metamac.notifications.rest.internal.v1_0.mapper.base;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.notifications.core.common.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;

public interface CommonDo2RestMapperV10 {

    public Resources externalItemEntityListToRest(List<ExternalItem> source) throws MetamacException;

    public Resource externalItemEntityToRest(ExternalItem source) throws MetamacException;

    public InternationalString stringEntityToInternationalStringRest(String source);

    public InternationalString internationalStringEntityToRest(InternationalString sources);

    public InternationalString internationalStringEntityToRest(org.siemac.metamac.notifications.core.common.domain.InternationalString sources);

    public ResourceLink uriToResourceLink(String kind, String href);

}
