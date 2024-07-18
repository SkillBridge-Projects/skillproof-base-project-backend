//package com.skillproof.skillproofapi.services.userRole;
//
//import com.skillproof.skillproofapi.constants.ObjectConstants;
//import com.skillproof.skillproofapi.constants.UserConstants;
//import com.skillproof.skillproofapi.exceptions.InvalidRequestException;
//import com.skillproof.skillproofapi.exceptions.ResourceNotFoundException;
//import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
//import com.skillproof.skillproofapi.model.entity.Role;
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.repositories.role.RoleRepository;
//import com.skillproof.skillproofapi.repositories.user.UserRepository;
//import com.skillproof.skillproofapi.repositories.userRole.UserRoleRepository;
//import org.apache.commons.lang3.ObjectUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//@Transactional
//public class UserRoleServiceImpl implements UserRoleService {
//
//    private static final Logger LOG = LoggerFactory.getLogger(UserRoleServiceImpl.class);
//
//    private UserRoleRepository userRoleRepository;
//    private UserRepository userRepository;
//    private RoleRepository roleRepository;
//
//    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository,
//                               RoleRepository roleRepository) {
//        this.userRoleRepository = userRoleRepository;
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public void grantUserToRole(String username, Long roleId) {
//        User user = userRepository.getUserByUsername(username);
//        if (ObjectUtils.isEmpty(user)) {
//            LOG.error("User with username {} not found", username);
//            throw new UserNotFoundException(UserConstants.USER_USERNAME, username);
//        }
//        Role role = roleRepository.getRoleById(roleId);
//        if (ObjectUtils.isEmpty(role)) {
//            LOG.error("Role with id {} not found", roleId);
//            throw new ResourceNotFoundException(ObjectConstants.ROLE, ObjectConstants.ID, roleId);
//        }
//        Boolean success = userRoleRepository.grantUserToRole(user.getId(), role.getId());
//        if (!success) {
//            throw new InvalidRequestException("Granting User to Role failed.");
//        }
//    }
//}
