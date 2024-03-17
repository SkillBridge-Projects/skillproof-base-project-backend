package com.skillproof.skillproofapi.services.role;

import com.skillproof.skillproofapi.model.request.role.CreateRoleRequest;
import com.skillproof.skillproofapi.model.request.role.RoleResponse;

public interface RoleService {

    RoleResponse createRole(CreateRoleRequest createRoleRequest);

    RoleResponse getRoleById(long id);
}
