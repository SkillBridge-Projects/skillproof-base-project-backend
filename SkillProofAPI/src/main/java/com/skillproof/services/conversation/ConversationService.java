package com.skillproof.services.conversation;

import com.skillproof.model.request.conversation.ConversationResponse;
import com.skillproof.model.request.conversation.CreateConversationRequest;

import java.util.List;

public interface ConversationService {

    ConversationResponse createConversation(CreateConversationRequest conversationRequest);

    List<ConversationResponse> getConversationsForUser(String userId);

    void deleteConversationById(Long id);

    List<ConversationResponse> listAllConversations();
}
