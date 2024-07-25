//package com.skillproof.skillproofapi.repositories.userRole;
//
//import com.skillproof.skillproofapi.exceptions.InvalidRequestException;
//import com.skillproof.skillproofapi.model.entity.Role;
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.model.entity.UserRole;
//import com.skillproof.skillproofapi.repositories.UserRoleDao;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public class UserRoleRepositoryImpl implements UserRoleRepository {
//
//    private static final Logger LOG = LoggerFactory.getLogger(UserRoleRepositoryImpl.class);
//
//    private UserRoleDao userRoleDao;
//
//    public UserRoleRepositoryImpl(UserRoleDao userRoleDao){
//        this.userRoleDao = userRoleDao;
//    }
//
//    @Override
//    public List<Role> getRolesForUser(Long userId){
//        return userRoleDao.findByUserId(userId);
//    }
//
//    @Override
//    public List<User> getUsersForRole(Long roleId){
//        return userRoleDao.findByRoleId(roleId);
//    }
//
//    @Override
//    public Boolean grantUserToRole(Long userId, Long roleId) {
//        Boolean success;
//        try {
//            UserRole userRole = new UserRole();
////            userRole.setRoleId(roleId);
////            userRole.setUserId(userId);
//            userRole.setCreatedDate(LocalDateTime.now());
//            userRole.setUpdatedDate(LocalDateTime.now());
//            userRoleDao.saveAndFlush(userRole);
//            success = true;
//        } catch (Exception exception){
//            success = false;
//        }
//        return success;
//    }
//
//}
