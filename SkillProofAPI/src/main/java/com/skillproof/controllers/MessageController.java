package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.message.MessageResponse;
import com.skillproof.model.request.message.CreateMessageRequest;
import com.skillproof.model.request.message.UpdateMessageRequest;
import com.skillproof.services.message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Message", description = "Stores Messages of users in skillProof App")
public class MessageController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(value = "/messages/send-message", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Message of a user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> createMessage(@RequestBody @Valid CreateMessageRequest createMessageRequest) {
        LOG.debug("Start of createMessage method.");
        MessageResponse messageResponse = messageService.createMessage(createMessageRequest);
        LOG.debug("End of createMessage method.");
        return created(messageResponse);
    }

    @GetMapping(value = "/conversation/{conversationId}/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "list Messages for post",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<?> listMessagesByConversationId(@PathVariable Long conversationId) {
        LOG.debug("Start of listMessagesByConversationId method.");
        List<MessageResponse> messageResponse = messageService.listMessagesByConversationId(conversationId);
        if (CollectionUtils.isEmpty(messageResponse)) {
            LOG.debug("End of listMessagesByConversationId method.");
            return noContent();
        }
        LOG.debug("End of listMessagesByConversationId method.");
        return ok(messageResponse);
    }

    @GetMapping(value = "/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Message by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<?> getMessageById(@PathVariable Long id) {
        LOG.debug("Start of getMessageById method.");
        MessageResponse MessageResponse = messageService.getMessageById(id);
        LOG.debug("End of getMessageById method.");
        return ok(MessageResponse);
    }

    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Messages",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<List<MessageResponse>> listAllMessages() {
        List<MessageResponse> messageResponses = messageService.listAllMessages();
        return ok(messageResponses);
    }

    @PatchMapping(value = "/messages/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update message of a user in a conversation",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
            }
    )
    public ResponseEntity<MessageResponse> updateMessage(@PathVariable Long id,
                                                         @RequestBody UpdateMessageRequest updateMessageRequest) {
        LOG.debug("Start of updateMessage method.");
        MessageResponse messageResponse = messageService.updateMessage(id, updateMessageRequest);
        LOG.debug("End of updateMessage method.");
        return ok(messageResponse);
    }

    @DeleteMapping(value = "/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete message of an user by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteMessageById(@PathVariable Long id) {
        messageService.deleteMessageById(id);
        return ok();
    }
}
