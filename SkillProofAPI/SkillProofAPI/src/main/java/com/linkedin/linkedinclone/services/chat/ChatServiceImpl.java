package com.linkedin.linkedinclone.services.chat;

import com.linkedin.linkedinclone.constants.ErrorMessageConstants;
import com.linkedin.linkedinclone.constants.ObjectConstants;
import com.linkedin.linkedinclone.exceptions.ResourceNotFoundException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Chat;
import com.linkedin.linkedinclone.model.Message;
import com.linkedin.linkedinclone.model.Picture;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.ChatRepository;
import com.linkedin.linkedinclone.repositories.MessageRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;


    @Override
    public Set<Chat> getAllChats(Long id) {
        User currentUser = userRepository.findById(id)
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
                        profilePicture.getType(), decompressBytes(profilePicture.getBytes()));
                profilePicture.setCompressed(false);
                u.setProfilePicture(tempPicture);
            }
        }
    }

    @Override
    public Chat getChatByUser(Long userId, Long chatId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.USER, userId)));

        return chatRepository.findById(chatId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.CHAT, chatId)));
    }

    @Override
    public Message newMessage(Long userId, Long chatId, Message message) {
        User currentUser = userRepository.findById(userId)
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
