package com.skillproof.skillproofapi.repositories.user;

import com.skillproof.skillproofapi.model.entity.Connection;
import com.skillproof.skillproofapi.model.entity.User;

import java.util.List;

public interface UserRepository {

    User createUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String userName);

    List<User> listAllUsers();

    List<Connection> listConnectionsForUser(Long userId);
}
