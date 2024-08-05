package com.skillproof.repositories;

import com.skillproof.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationDao extends JpaRepository<Conversation, Long> {

    List<Conversation> findByParticipantsId(String userId);
}
