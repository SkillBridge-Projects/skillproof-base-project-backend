package com.skillproof.skillproofapi.model.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String role;


}
