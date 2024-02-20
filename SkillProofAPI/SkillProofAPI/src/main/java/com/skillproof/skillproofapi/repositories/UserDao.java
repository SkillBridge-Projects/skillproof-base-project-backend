package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.enumerations.RoleType;
import com.skillproof.skillproofapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username  = :email ")
    User findUserByUsername(@PathVariable String email);

    @Query("SELECT u FROM User u WHERE u.username  = :email ")
    Optional<User> findByUsername(@PathVariable String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :name ")
    List<User> findByRole(@PathVariable RoleType name);
}
