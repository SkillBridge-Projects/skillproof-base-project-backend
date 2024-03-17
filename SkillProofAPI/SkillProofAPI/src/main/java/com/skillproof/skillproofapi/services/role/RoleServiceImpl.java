package com.skillproof.skillproofapi.services.role;

import com.skillproof.skillproofapi.exceptions.ResourceNotFoundException;
import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
import com.skillproof.skillproofapi.model.entity.Role;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.role.CreateRoleRequest;
import com.skillproof.skillproofapi.model.request.role.RoleResponse;
import com.skillproof.skillproofapi.repositories.role.RoleRepository;
import com.skillproof.skillproofapi.services.user.UserService;
import com.skillproof.skillproofapi.utils.ResponseConverter;
import com.skillproof.skillproofapi.utils.Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(UserService userService, RoleRepository roleRepository){
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @Override
    public RoleResponse createRole(CreateRoleRequest createRoleRequest) {
        Role role = new Role();
        role.setName(createRoleRequest.getRoleType());
        if (createRoleRequest.getUserName() != null){
            User user = userService.findUserByUsername(createRoleRequest.getUserName());
            if (ObjectUtils.isEmpty(user)){
                throw new UserNotFoundException("User with username " + createRoleRequest.getUserName() + " Not found");
            }
            role.setUser(user);
        }
        role = roleRepository.createRole(role);
        return ResponseConverter.copyProperties(role, RoleResponse.class);
    }

    @Override
    public RoleResponse getRoleById(long id) {
        Role role = roleRepository.getRoleById(id);
        if (ObjectUtils.isEmpty(role)){
            throw new ResourceNotFoundException("Role with Id " + id + " Not found");
        }
        return ResponseConverter.copyProperties(role, RoleResponse.class);
    }


}
