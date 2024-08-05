package com.skillproof.services.message;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.exceptions.UserNotFoundException;
import com.skillproof.model.entity.Comment;
import com.skillproof.model.entity.Conversation;
import com.skillproof.model.entity.Message;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.comment.CommentResponse;
import com.skillproof.model.request.message.CreateMessageRequest;
import com.skillproof.model.request.message.MessageResponse;
import com.skillproof.model.request.message.UpdateMessageRequest;
import com.skillproof.repositories.conversation.ConversationRepository;
import com.skillproof.repositories.message.MessageRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.comment.CommentServiceImpl;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(ConversationRepository conversationRepository, UserRepository userRepository,
                              MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }


    @Override
    public MessageResponse createMessage(CreateMessageRequest createMessageRequest) {
        Conversation conversation = conversationRepository.getConversationById(createMessageRequest.getConversationId());
        if (ObjectUtils.isEmpty(conversation)) {
            LOG.error("Conversation with id {} not found", createMessageRequest.getConversationId());
            throw new ResourceNotFoundException(ObjectConstants.CONVERSATION, ObjectConstants.ID,
                    createMessageRequest.getConversationId());
        }

        User sender = userRepository.getUserById(createMessageRequest.getSenderId());
        if (ObjectUtils.isEmpty(sender)) {
            LOG.error("User with id {} not found.", createMessageRequest.getSenderId());
            throw new UserNotFoundException(ObjectConstants.USER, createMessageRequest.getSenderId());
        }

        Message message = createMessageEntity(createMessageRequest, conversation, sender);
        message = messageRepository.createMessage(message);
        return getResponse(message);
    }

    private Message createMessageEntity(CreateMessageRequest createMessageRequest, Conversation conversation, User sender) {
        Message message = new Message();
        message.setContent(createMessageRequest.getContent());
        message.setSender(sender);
        message.setConversation(conversation);
        return message;
    }

    @Override
    public List<MessageResponse> listMessagesByConversationId(Long conversationId) {
        List<Message> messages = messageRepository.getMessagesForConversation(conversationId);
        return getResponseList(messages);
    }

    @Override
    public MessageResponse getMessageById(Long id) {
        Message message = messageRepository.getMessageById(id);
        if (ObjectUtils.isEmpty(message)) {
            LOG.error("Message with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.MESSAGE, ObjectConstants.ID, id);
        }
        return getResponse(message);
    }

    @Override
    public List<MessageResponse> listAllMessages() {
        List<Message> messages = messageRepository.listAllMessages();
        return getResponseList(messages);
    }

    @Override
    public MessageResponse updateMessage(Long id, UpdateMessageRequest updateMessageRequest) {
        Message message = messageRepository.getMessageById(id);
        if (ObjectUtils.isEmpty(message)) {
            LOG.error("Message with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.MESSAGE, ObjectConstants.ID, id);
        }

        if (StringUtils.isNotEmpty(updateMessageRequest.getContent())) {
            message.setContent(updateMessageRequest.getContent());
        }

        Message updatedMessage = messageRepository.updateMessage(message);
        return getResponse(updatedMessage);
    }

    @Override
    public void deleteMessageById(Long id) {
        messageRepository.deleteMessageById(id);
    }

    private MessageResponse getResponse(Message message) {
        MessageResponse messageResponse = ResponseConverter
                .copyProperties(message, MessageResponse.class);
        messageResponse.setSenderId(message.getSender().getId());
        messageResponse.setConversationId(message.getConversation().getId());
        return messageResponse;
    }

    private List<MessageResponse> getResponseList(List<Message> messages) {
        return messages.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }
}
