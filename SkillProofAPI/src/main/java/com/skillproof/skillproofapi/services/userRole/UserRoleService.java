package com.skillproof.skillproofapi.services.userRole;

public interface UserRoleService {

    void grantUserToRole(String username, Long roleId);
}
