package com.skillproof.skillproofapi.model.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCredentials {

    private String userName;
    private String password;
}
