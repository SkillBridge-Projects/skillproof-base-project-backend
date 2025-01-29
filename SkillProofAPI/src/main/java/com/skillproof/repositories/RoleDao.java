//package com.skillproof.repositories;
//
//import com.skillproof.enums.RoleType;
//import com.skillproof.model.entity.Role;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.web.bind.annotation.PathVariable;
//
//public interface RoleDao extends JpaRepository<Role, Long> {
//    @Query("SELECT r FROM Role r WHERE r.name  = :rn ")
//    Role findByName(@PathVariable RoleType rn);
//}
