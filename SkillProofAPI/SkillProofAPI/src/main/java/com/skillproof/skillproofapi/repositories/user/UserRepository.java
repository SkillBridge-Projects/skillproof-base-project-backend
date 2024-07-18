package com.skillproof.skillproofapi.repositories.user;

import com.skillproof.skillproofapi.model.entity.User;

import java.util.List;

public interface UserRepository {

    User createUser(User user);

    User getUserById(String id);

    User getUserByUsername(String userName);

    List<User> listAllUsers();

    void deleteUserById(String id);

    User updateUser(User user);
}
