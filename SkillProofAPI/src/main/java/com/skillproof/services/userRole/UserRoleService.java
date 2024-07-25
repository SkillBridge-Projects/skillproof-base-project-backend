package com.skillproof.services.userRole;

public interface UserRoleService {

    void grantUserToRole(String username, Long roleId);
}
