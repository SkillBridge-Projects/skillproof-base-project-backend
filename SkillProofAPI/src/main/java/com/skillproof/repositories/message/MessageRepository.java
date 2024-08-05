package com.skillproof.repositories.message;

import com.skillproof.model.entity.Message;

import java.util.List;

public interface MessageRepository {

    List<Message> getMessagesForConversation(Long conversationId);

    void deleteMessageById(Long id);

    Message updateMessage(Message message);

    Message getMessageById(Long id);

    List<Message> listAllMessages();

    Message createMessage(Message message);
}
