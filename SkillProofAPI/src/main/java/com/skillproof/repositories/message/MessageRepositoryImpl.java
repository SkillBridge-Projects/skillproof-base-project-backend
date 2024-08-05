package com.skillproof.repositories.message;

import com.skillproof.model.entity.Message;
import com.skillproof.repositories.MessageDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    private final MessageDao messageDao;

    public MessageRepositoryImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }


    @Override
    public List<Message> getMessagesForConversation(Long conversationId) {
        return messageDao.findByConversationId(conversationId);
    }

    @Override
    public void deleteMessageById(Long id) {
        messageDao.deleteById(id);
    }

    @Override
    public Message updateMessage(Message message) {
        return messageDao.saveAndFlush(message);
    }

    @Override
    public Message getMessageById(Long id) {
        return messageDao.findById(id).orElse(null);
    }

    @Override
    public List<Message> listAllMessages() {
        return messageDao.findAll();
    }

    @Override
    public Message createMessage(Message message) {
        return messageDao.saveAndFlush(message);
    }
}
