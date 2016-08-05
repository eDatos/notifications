package org.siemac.metamac.notices.rest.internal.constants;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.api.constants.RestApiConstants;

public class NoticesRestConstants extends RestApiConstants {

    public static final ServiceContext SERVICE_CONTEXT      = new ServiceContext("restNotices", "restNotices", "restNotices");

    public static final String         API_NAME             = "notices";
    public static final String         API_VERSION_1_0      = "v1.0";

    public static final String         KIND_NOTICE          = API_NAME + KIND_SEPARATOR + "notice";

    public static final String         LINK_SUBPATH_NOTICES = "notices";
}
