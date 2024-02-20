package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository  extends JpaRepository<Connection, Long> {
}
