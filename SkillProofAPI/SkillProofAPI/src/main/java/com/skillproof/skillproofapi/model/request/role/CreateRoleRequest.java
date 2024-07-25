package com.skillproof.skillproofapi.model.request.role;

import com.skillproof.skillproofapi.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleRequest {

    private RoleType roleType;
    private String description;
}
