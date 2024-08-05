package com.skillproof.services.conversation;

import com.skillproof.constants.ObjectConstants;
import com.skillproof.exceptions.ResourceNotFoundException;
import com.skillproof.model.entity.Conversation;
import com.skillproof.model.entity.User;
import com.skillproof.model.request.conversation.ConversationResponse;
import com.skillproof.model.request.conversation.CreateConversationRequest;
import com.skillproof.repositories.conversation.ConversationRepository;
import com.skillproof.repositories.user.UserRepository;
import com.skillproof.services.comment.CommentServiceImpl;
import com.skillproof.utils.ResponseConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConversationServiceImpl implements ConversationService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public ConversationServiceImpl(UserRepository userRepository, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public ConversationResponse createConversation(CreateConversationRequest conversationRequest) {
        List<User> participants = userRepository.findParticipants(conversationRequest.getParticipantIds());

        Set<String> foundIds = participants.stream().map(User::getId).collect(Collectors.toSet());

        // Find missing participant IDs
        List<String> missingIds = conversationRequest.getParticipantIds().stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toList());

        if (!missingIds.isEmpty()) {
            String missingIdsMessage = "Users with IDs not found: " + missingIds;
            LOG.error(missingIdsMessage);
            throw new ResourceNotFoundException(missingIdsMessage);
        }
        Conversation conversation = new Conversation();
        conversation.setParticipants(participants);
        conversation =  conversationRepository.createConversation(conversation);
        return getResponse(conversation);
    }

    @Override
    public List<ConversationResponse> getConversationsForUser(String userId) {
        List<Conversation> conversations = conversationRepository.findByParticipantsId(userId);
        return getResponseList(conversations);
    }

    @Override
    public void deleteConversationById(Long id) {
        Conversation conversation = conversationRepository.getConversationById(id);
        if (ObjectUtils.isEmpty(conversation)){
            LOG.error("Conversation with id {} not found", id);
            throw new ResourceNotFoundException(ObjectConstants.CONVERSATION, ObjectConstants.ID, id);
        }
        conversationRepository.deleteConversationById(id);
    }

    @Override
    public List<ConversationResponse> listAllConversations() {
        List<Conversation> conversations = conversationRepository.listAllConversations();
        return getResponseList(conversations);
    }

    private ConversationResponse getResponse(Conversation conversation) {
        ConversationResponse response = ResponseConverter
                .copyProperties(conversation, ConversationResponse.class);
        List<String> participantIds = conversation.getParticipants().stream()
                .map(User::getId).collect(Collectors.toList());
        response.setParticipantIds(participantIds);
        return response;
    }

    private List<ConversationResponse> getResponseList(List<Conversation> conversations) {
        return conversations.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }
}
