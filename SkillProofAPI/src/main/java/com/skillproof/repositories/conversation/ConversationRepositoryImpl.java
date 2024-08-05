package com.skillproof.repositories.conversation;

import com.skillproof.model.entity.Conversation;
import com.skillproof.repositories.ConversationDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConversationRepositoryImpl implements ConversationRepository {

    private final ConversationDao conversationDao;

    public ConversationRepositoryImpl(ConversationDao conversationDao) {
        this.conversationDao = conversationDao;
    }

    @Override
    public Conversation createConversation(Conversation conversation) {
        return conversationDao.saveAndFlush(conversation);
    }

    @Override
    public List<Conversation> findByParticipantsId(String userId) {
        return conversationDao.findByParticipantsId(userId);
    }

    @Override
    public Conversation getConversationById(Long id) {
        return conversationDao.findById(id).orElse(null);
    }

    @Override
    public void deleteConversationById(Long id) {
        conversationDao.deleteById(id);
    }

    @Override
    public List<Conversation> listAllConversations() {
        return conversationDao.findAll();
    }
}
