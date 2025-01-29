package com.skillproof.repositories.user;

import com.skillproof.enums.RoleType;
import com.skillproof.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User createUser(User user);

    User getUserById(String id);

    User getUserByUsername(String userName);

    List<User> listAllUsers();

    void deleteUserById(String id);

    User updateUser(User user);

    List<User> listUsersByRole(RoleType roleType);

    Optional<User> getUserByProfilePicture(String profilePicture);

    User updateProfilePicture(String userId, String profilePicturePath);

    Optional<User> sender(String senderId);

    Optional<User> receiver(String receiverId);

    List<User> findByEmailAddressContaining(String message);

    Optional<User> findById(String userId);
}
