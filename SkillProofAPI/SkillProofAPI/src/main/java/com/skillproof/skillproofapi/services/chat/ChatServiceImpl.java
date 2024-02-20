package com.skillproof.skillproofapi.services.chat;

import com.skillproof.skillproofapi.constants.ErrorMessageConstants;
import com.skillproof.skillproofapi.constants.ObjectConstants;
import com.skillproof.skillproofapi.exceptions.ResourceNotFoundException;
import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
import com.skillproof.skillproofapi.model.entity.Chat;
import com.skillproof.skillproofapi.model.entity.Message;
import com.skillproof.skillproofapi.model.entity.Picture;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.repositories.ChatRepository;
import com.skillproof.skillproofapi.repositories.MessageRepository;
import com.skillproof.skillproofapi.repositories.UserDao;
import com.skillproof.skillproofapi.utils.PictureSave;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserDao userDao;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;


    @Override
    public Set<Chat> getAllChats(Long id) {
        User currentUser = userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.USER, id)));
        for (Chat chat : currentUser.getChats()) {
            for (User user : chat.getUsers()) {
                setUserProfilePicture(user);
            }
            for (Message m : chat.getMessages()) {
                User user = m.getUserMadeBy();
                setUserProfilePicture(user);
            }
        }
        return currentUser.getChats();
    }

    private static void setUserProfilePicture(User u) {
        Picture profilePicture = u.getProfilePicture();
        if (profilePicture != null) {
            if (profilePicture.isCompressed()) {
                Picture tempPicture = new Picture(profilePicture.getId(), profilePicture.getName(),
                        profilePicture.getType(), PictureSave.decompressBytes(profilePicture.getBytes()));
                profilePicture.setCompressed(false);
                u.setProfilePicture(tempPicture);
            }
        }
    }

    @Override
    public Chat getChatByUser(Long userId, Long chatId) {
        userDao.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.USER, userId)));

        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.CHAT, chatId)));
    }

    @Override
    public Message newMessage(Long userId, Long chatId, Message message) {
        User currentUser = userDao.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.USER, userId)));

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.CHAT, chatId)));
        message.setUserMadeBy(currentUser);
        message.setChat(chat);
        messageRepository.save(message);
        return message;
    }
}
