package com.skillproof.services.user;

import com.skillproof.enums.RoleType;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.profile.UserProfile;
import com.skillproof.model.request.user.CreateUserRequest;
import com.skillproof.model.request.user.UpdateUserRequest;
import com.skillproof.model.request.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserResponse getUserById(String id);

    UserResponse getUserByEmailAddress(String emailAddress);

    UserResponse createUser(CreateUserRequest createUserRequest);

    List<UserResponse> listAllUsers();

    UserResponse updateUser(String id, UpdateUserRequest updateUserRequest);

    void deleteUserById(String id);

    List<UserResponse> listUsersByRole(RoleType role);

    UserProfile getUserProfileByUserId(String id);

    UserProfile updateProfilePicture(String id, MultipartFile profilePicture) throws Exception;

    User getUserByProfilePicture(String profilePicture);

    void sendUserCreationEmail(String emailAddress);

}