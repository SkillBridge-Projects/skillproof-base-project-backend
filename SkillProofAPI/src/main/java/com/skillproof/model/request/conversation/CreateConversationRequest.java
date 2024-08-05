package com.skillproof.model.request.conversation;

import com.skillproof.validators.Messages;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateConversationRequest {

    @NotBlank(message = Messages.NO_EMPTY_PROPERTY)
    private List<String> participantIds;
}
