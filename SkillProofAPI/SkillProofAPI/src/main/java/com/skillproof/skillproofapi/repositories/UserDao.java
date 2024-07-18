package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {

//    @Query("SELECT u FROM User u JOIN u.role r WHERE r.name = :name ")
//    List<User> findByRole(@PathVariable RoleType name);

    Optional<User> findByEmailAddressIgnoreCase(String userName);

}
