package com.skillproof.repositories;

import com.skillproof.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, Long> {

    List<Message> findByConversationId(Long conversationId);
}
