package com.skillproof.model.request.chat;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateChatRequest {

    private Long id;
    private Set<Integer> userIds;
    private Set<Integer> messageIds;
}
