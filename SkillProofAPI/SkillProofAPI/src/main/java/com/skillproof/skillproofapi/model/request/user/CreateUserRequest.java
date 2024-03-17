package com.skillproof.skillproofapi.model.request.user;

import com.skillproof.skillproofapi.enumerations.RoleType;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateUserRequest {

    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
}
