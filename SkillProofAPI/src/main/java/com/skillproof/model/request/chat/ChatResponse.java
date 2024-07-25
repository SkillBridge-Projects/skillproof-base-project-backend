package com.skillproof.model.request.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatResponse extends CreateChatRequest {

    private Long id;
    private Timestamp timestamp;
    private Set<Integer> users;
    private Set<Integer> messageIds;

}
