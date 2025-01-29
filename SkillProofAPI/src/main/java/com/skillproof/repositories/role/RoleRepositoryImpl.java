//package com.skillproof.repositories.role;
//
//import com.skillproof.model.entity.Role;
//import com.skillproof.repositories.RoleDao;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class RoleRepositoryImpl implements RoleRepository {
//
//    private final RoleDao roleDao;
//
//    public RoleRepositoryImpl(RoleDao roleDao){
//        this.roleDao = roleDao;
//    }
//
//    @Override
//    public Role createRole(Role role) {
//        return roleDao.saveAndFlush(role);
//    }
//
//    @Override
//    public Role getRoleById(long id) {
//        Optional<Role> role = roleDao.findById(id);
//        return role.orElse(null);
//    }
//
//    @Override
//    public Role updateRole(Role role) {
//        return roleDao.saveAndFlush(role);
//    }
//
//    @Override
//    public List<Role> listAllRoles() {
//        return roleDao.findAll();
//    }
//}
