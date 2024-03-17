package com.skillproof.skillproofapi.services.admin;

import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.user.UserResponse;

import java.util.List;

public interface AdminService {
    List<UserResponse> listUsers();
}
