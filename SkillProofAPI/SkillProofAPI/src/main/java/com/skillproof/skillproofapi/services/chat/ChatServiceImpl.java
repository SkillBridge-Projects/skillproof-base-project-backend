//package com.skillproof.skillproofapi.services.chat;
//
//import com.skillproof.skillproofapi.constants.ObjectConstants;
//import com.skillproof.skillproofapi.exceptions.ResourceNotFoundException;
//import com.skillproof.skillproofapi.exceptions.UserNotFoundException;
//import com.skillproof.skillproofapi.model.entity.Chat;
//import com.skillproof.skillproofapi.model.entity.Message;
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.repositories.ChatDao;
//import com.skillproof.skillproofapi.repositories.MessageDao;
//import com.skillproof.skillproofapi.repositories.UserDao;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class ChatServiceImpl implements ChatService {
//
//    private final UserDao userDao;
//    private final ChatDao chatDao;
//    private final MessageDao messageDao;
//
//
//    @Override
//    public List<Chat> getAllChats(Long id) {
//        User currentUser = userDao.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(ObjectConstants.USER, id));
////        for (Chat chat : currentUser.getChats()) {
////            for (User user : chat.getUsers()) {
////                setUserProfilePicture(user);
////            }
////            for (Message m : chat.getMessages()) {
////                User user = m.getUserMadeBy();
////                setUserProfilePicture(user);
////            }
////        }
//        return (List<Chat>) currentUser;
//    }
//
////    private static void setUserProfilePicture(User u) {
////        Picture profilePicture = u.getProfilePicture();
////        if (profilePicture != null) {
////            if (profilePicture.isCompressed()) {
////                Picture tempPicture = new Picture(profilePicture.getId(), profilePicture.getName(),
////                        profilePicture.getType(), PictureSave.decompressBytes(profilePicture.getBytes()));
////                profilePicture.setCompressed(false);
////                u.setProfilePicture(tempPicture);
////            }
////        }
////    }
//
//    @Override
//    public Chat getChatByUser(Long userId, Long chatId) {
//        userDao.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(ObjectConstants.USER, userId));
//
//        return chatDao.findById(chatId)
//                .orElseThrow(() -> new ResourceNotFoundException(ObjectConstants.CHAT, ObjectConstants.ID, chatId));
//    }
//
//    @Override
//    public Message newMessage(Long userId, Long chatId, Message message) {
//        User currentUser = userDao.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(ObjectConstants.USER, userId));
//
//        Chat chat = chatDao.findById(chatId)
//                .orElseThrow(() -> new ResourceNotFoundException(ObjectConstants.CHAT, ObjectConstants.ID, chatId));
////        message.setUserMadeBy(currentUser);
//        message.setChat(chat);
//        messageDao.save(message);
//        return message;
//    }
//}
