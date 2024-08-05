package com.skillproof.repositories.conversation;

import com.skillproof.model.entity.Conversation;

import java.util.List;

public interface ConversationRepository {
    Conversation createConversation(Conversation conversation);

    List<Conversation> findByParticipantsId(String userId);

    Conversation getConversationById(Long id);

    void deleteConversationById(Long id);

    List<Conversation> listAllConversations();
}
