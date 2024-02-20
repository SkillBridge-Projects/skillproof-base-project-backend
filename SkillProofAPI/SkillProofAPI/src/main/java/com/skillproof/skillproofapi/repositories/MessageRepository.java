package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
