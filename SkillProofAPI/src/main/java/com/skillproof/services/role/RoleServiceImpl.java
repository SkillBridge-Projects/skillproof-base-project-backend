//package com.skillproof.services.role;
//
//import com.skillproof.constants.ObjectConstants;
//import com.skillproof.exceptions.ResourceNotFoundException;
//import com.skillproof.model.entity.Role;
//import com.skillproof.model.request.role.CreateRoleRequest;
//import com.skillproof.model.request.role.RoleResponse;
//import com.skillproof.model.request.role.UpdateRoleRequest;
//import com.skillproof.repositories.role.RoleRepository;
//import com.skillproof.services.user.UserService;
//import com.skillproof.utils.ResponseConverter;
//import org.apache.commons.lang3.ObjectUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@Transactional
//public class RoleServiceImpl implements RoleService {
//
//    private static final Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);
//
//    private final UserService userService;
//    private final RoleRepository roleRepository;
//
//    public RoleServiceImpl(UserService userService, RoleRepository roleRepository) {
//        this.userService = userService;
//        this.roleRepository = roleRepository;
//    }
//
//    @Override
//    public RoleResponse createRole(CreateRoleRequest createRoleRequest) {
//        Role role = new Role();
//        role.setName(createRoleRequest.getRoleType());
//        role.setDescription(createRoleRequest.getDescription());
//        role.setCreatedDate(LocalDateTime.now());
//        role.setUpdatedDate(LocalDateTime.now());
//        role = roleRepository.createRole(role);
//        return ResponseConverter.copyProperties(role, RoleResponse.class);
//    }
//
//    @Override
//    public RoleResponse getRoleById(long id) {
//        Role role = roleRepository.getRoleById(id);
//        if (ObjectUtils.isEmpty(role)) {
//            LOG.error("Role with id {} not found", id);
//            throw new ResourceNotFoundException(ObjectConstants.ROLE, ObjectConstants.ID, id);
//        }
//        return ResponseConverter.copyProperties(role, RoleResponse.class);
//    }
//
//    @Override
//    public RoleResponse updateRole(Long roleId, UpdateRoleRequest updateRoleRequest) {
//        Role role = roleRepository.getRoleById(roleId);
//        if (ObjectUtils.isEmpty(role)) {
//            LOG.error("Role with id {} not found", roleId);
//            throw new ResourceNotFoundException(ObjectConstants.ROLE, ObjectConstants.ID, roleId);
//        }
//        role.setDescription(updateRoleRequest.getDescription());
//        return ResponseConverter.copyProperties(roleRepository.updateRole(role), RoleResponse.class);
//    }
//
//    @Override
//    public List<RoleResponse> listAllRoles() {
//        List<Role> roles = roleRepository.listAllRoles();
//        return ResponseConverter.copyListProperties(roles, RoleResponse.class);
//    }
//
//
//}
