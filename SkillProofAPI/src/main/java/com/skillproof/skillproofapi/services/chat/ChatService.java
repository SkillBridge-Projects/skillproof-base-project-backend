package com.skillproof.skillproofapi.services.chat;

import com.skillproof.skillproofapi.model.entity.Chat;
import com.skillproof.skillproofapi.model.entity.Message;

import java.util.Set;

public interface ChatService {

    Set<Chat> getAllChats(Long id);

    Chat getChatByUser(Long userId, Long chatId);

    Message newMessage(Long id, Long chatId, Message message);
}
