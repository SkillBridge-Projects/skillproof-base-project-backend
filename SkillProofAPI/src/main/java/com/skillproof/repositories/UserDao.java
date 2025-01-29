package com.skillproof.repositories;

import com.skillproof.enums.RoleType;
import com.skillproof.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {

    // Method to find by email (now uses 'emailAddress')
    List<User> findByEmailAddressContaining(String email);  // Updated method

    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);

    List<User> findAllByRole(RoleType roleType);

    Optional<User> findByProfilePicture(String profilePicture);
}
