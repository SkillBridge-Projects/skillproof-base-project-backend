package com.linkedin.linkedinclone.services.chat;

import com.linkedin.linkedinclone.model.Chat;
import com.linkedin.linkedinclone.model.Message;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

public interface ChatService {

    Set<Chat> getAllChats(Long id);

    Chat getChatByUser(Long userId, Long chatId);

    Message newMessage(Long id, Long chatId, Message message);
}
