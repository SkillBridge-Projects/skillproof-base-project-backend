package com.skillproof.skillproofapi.services.role;

import com.skillproof.skillproofapi.model.request.role.CreateRoleRequest;
import com.skillproof.skillproofapi.model.request.role.RoleResponse;
import com.skillproof.skillproofapi.model.request.role.UpdateRoleRequest;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(CreateRoleRequest createRoleRequest);

    RoleResponse getRoleById(long id);

    RoleResponse updateRole(Long roleId, UpdateRoleRequest updateRoleRequest);

    List<RoleResponse> listAllRoles();
}
