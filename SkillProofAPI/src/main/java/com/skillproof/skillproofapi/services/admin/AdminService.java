package com.skillproof.skillproofapi.services.admin;

import com.skillproof.skillproofapi.model.entity.User;

import java.util.List;

public interface AdminService {
    List<User> listUsers();
}
