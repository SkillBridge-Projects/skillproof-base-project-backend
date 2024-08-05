package com.skillproof.model.request;

import com.skillproof.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {
    private Long id;
    private String content;
    private String senderId;
    private MessageType type;
    private Long conversationId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
