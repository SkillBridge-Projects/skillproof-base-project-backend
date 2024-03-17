package com.skillproof.skillproofapi.repositories.role;

import com.skillproof.skillproofapi.model.entity.Role;
import com.skillproof.skillproofapi.repositories.RoleDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {



    private final RoleDao roleDao;

    public RoleRepositoryImpl(RoleDao roleDao){
        this.roleDao = roleDao;
    }
    @Override
    public Role createRole(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> role = roleDao.findById(id);
        return role.orElse(null);
    }
}
