package com.skillproof.services.role;

import com.skillproof.model.request.role.CreateRoleRequest;
import com.skillproof.model.request.role.RoleResponse;
import com.skillproof.model.request.role.UpdateRoleRequest;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(CreateRoleRequest createRoleRequest);

    RoleResponse getRoleById(long id);

    RoleResponse updateRole(Long roleId, UpdateRoleRequest updateRoleRequest);

    List<RoleResponse> listAllRoles();
}
