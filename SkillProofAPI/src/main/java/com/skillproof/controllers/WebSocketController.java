package com.skillproof.controllers;

import com.skillproof.enums.MessageType;
import com.skillproof.model.request.ChatMessage;
import com.skillproof.model.request.message.CreateMessageRequest;
import com.skillproof.model.request.message.MessageResponse;
import com.skillproof.model.request.message.UpdateMessageRequest;
import com.skillproof.services.message.MessageService;
import com.skillproof.utils.ResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class WebSocketController {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage) {
        // Save the message to the database
        MessageResponse savedMessage = messageService.createMessage(createMsgRequest(chatMessage));

        // Broadcast the message to all subscribed clients
        messagingTemplate.convertAndSend("/topic/public", convertToChatMessage(savedMessage));
    }

    @MessageMapping("/chat.editMessage")
    public void editMessage(ChatMessage chatMessage) {

        UpdateMessageRequest request = new UpdateMessageRequest();
        request.setContent(chatMessage.getContent());

        // Edit the message in the database
        MessageResponse editedMessage = messageService.updateMessage(chatMessage.getId(), request);

        // Broadcast the edited message to all subscribed clients
        messagingTemplate.convertAndSend("/topic/public", convertToChatMessage(editedMessage));
    }

    @MessageMapping("/chat.deleteMessage")
    public void deleteMessage(ChatMessage chatMessage) {
        // Delete the message from the database
        messageService.deleteMessageById(chatMessage.getId());

        // Broadcast the deletion to all subscribed clients
        chatMessage.setType(MessageType.DELETE);
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        chatMessage.setType(MessageType.JOIN);
        return chatMessage;
    }

    private ChatMessage convertToChatMessage(MessageResponse message) {
        return ResponseConverter
                .copyProperties(message, ChatMessage.class);
    }

    private CreateMessageRequest createMsgRequest(ChatMessage chatMessage){
        CreateMessageRequest request = new CreateMessageRequest();
        request.setConversationId(chatMessage.getConversationId());
        request.setContent(chatMessage.getContent());
        request.setSenderId(chatMessage.getSenderId());
        return request;
    }
}
