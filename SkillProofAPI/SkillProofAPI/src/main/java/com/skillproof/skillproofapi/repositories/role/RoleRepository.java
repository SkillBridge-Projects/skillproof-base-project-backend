package com.skillproof.skillproofapi.repositories.role;

import com.skillproof.skillproofapi.model.entity.Role;

public interface RoleRepository {
    Role createRole(Role role);

    Role getRoleById(long id);
}
