package com.skillproof.services.message;

import com.skillproof.model.request.message.CreateMessageRequest;
import com.skillproof.model.request.message.MessageResponse;
import com.skillproof.model.request.message.UpdateMessageRequest;

import java.util.List;

public interface MessageService {
    MessageResponse createMessage(CreateMessageRequest createMessageRequest);

    List<MessageResponse> listMessagesByConversationId(Long conversationId);

    MessageResponse getMessageById(Long id);

    List<MessageResponse> listAllMessages();

    MessageResponse updateMessage(Long id, UpdateMessageRequest updateMessageRequest);

    void deleteMessageById(Long id);
}
