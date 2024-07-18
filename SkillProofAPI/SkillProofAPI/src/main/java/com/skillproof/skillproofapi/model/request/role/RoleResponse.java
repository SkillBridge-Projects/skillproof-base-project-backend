package com.skillproof.skillproofapi.model.request.role;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoleResponse extends CreateRoleRequest {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
