package com.skillproof.repositories.user;

import com.skillproof.enums.RoleType;
import com.skillproof.model.entity.User;

import java.util.List;

public interface UserRepository {

    User createUser(User user);

    User getUserById(String id);

    User getUserByUsername(String userName);

    List<User> listAllUsers();

    void deleteUserById(String id);

    User updateUser(User user);

    List<User> listUsersByRole(RoleType roleType);

    List<User> findParticipants(List<String> participantIds);
}
