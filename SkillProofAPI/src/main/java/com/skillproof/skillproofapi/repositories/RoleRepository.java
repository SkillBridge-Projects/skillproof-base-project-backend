package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.enumerations.RoleType;
import com.skillproof.skillproofapi.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

public interface RoleRepository extends JpaRepository<Role, Long>  {
    @Query("SELECT r FROM Role r WHERE r.name  = :rn ")
    Role findByName(@PathVariable RoleType rn);
}
