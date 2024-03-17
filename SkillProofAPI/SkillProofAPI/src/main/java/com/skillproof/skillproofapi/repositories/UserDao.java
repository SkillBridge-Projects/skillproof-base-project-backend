package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.enumerations.RoleType;
import com.skillproof.skillproofapi.model.entity.Connection;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.model.request.connection.ConnectionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName  = :email ")
    User findUserByUserName(@PathVariable String email);

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.name = :name ")
    List<User> findByRole(@PathVariable RoleType name);

    Optional<User> findByUserName(String userName);

//    @Query("SELECT u.users FROM User u WHERE u.id = :userId ")
    List<Connection> findUsersById(Long userId);
}
