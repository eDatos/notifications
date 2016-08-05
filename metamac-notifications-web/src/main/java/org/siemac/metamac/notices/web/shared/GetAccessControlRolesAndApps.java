package org.siemac.metamac.notices.web.shared;

import java.util.List;

import org.siemac.metamac.notices.web.shared.dto.AppDto;
import org.siemac.metamac.notices.web.shared.dto.RoleDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetAccessControlRolesAndApps {

    @Out(1)
    List<RoleDto> roles;

    @Out(2)
    List<AppDto>  apps;
}
