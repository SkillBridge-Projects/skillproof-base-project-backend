package com.skillproof.model.request.role;

import com.skillproof.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleRequest {

    private RoleType roleType;
    private String description;
}
