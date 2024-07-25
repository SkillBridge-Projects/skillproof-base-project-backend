package com.skillproof.model.request.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateMessageRequest {

    private Long id;

    private String content;

    private Long userId;

    private Long chatId;
}
