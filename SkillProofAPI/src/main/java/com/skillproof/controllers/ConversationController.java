package com.skillproof.controllers;

import com.skillproof.constants.SwaggerConstants;
import com.skillproof.model.request.conversation.ConversationResponse;
import com.skillproof.model.request.conversation.CreateConversationRequest;
import com.skillproof.services.conversation.ConversationService;
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
@Tag(name = "Conversation", description = "Stores Conversations of users in skillProof App")
public class ConversationController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ConversationController.class);

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping(value = "/conversations", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create conversation between users",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = ConversationResponse.class)))
            }
    )
    public ResponseEntity<ConversationResponse> createConversation(@RequestBody @Valid CreateConversationRequest createConversationRequest) {
        LOG.debug("Start of createConversation method.");
        ConversationResponse commentResponse = conversationService.createConversation(createConversationRequest);
        LOG.debug("End of createConversation method.");
        return created(commentResponse);
    }

    @GetMapping(value = "/users/{userId}/conversations", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "list Conversations for user",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = ConversationResponse.class)))
            }
    )
    public ResponseEntity<?> listConversationsByUserId(@PathVariable String userId) {
        LOG.debug("Start of listConversationsByUserId method.");
        List<ConversationResponse> commentResponse = conversationService.getConversationsForUser(userId);
        if (CollectionUtils.isEmpty(commentResponse)) {
            LOG.debug("End of listConversationsByUserId method.");
            return noContent();
        }
        LOG.debug("End of listConversationsByUserId method.");
        return ok(commentResponse);
    }

    @DeleteMapping(value = "/conversations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete conversation of users by id",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_DELETE)
            }
    )
    public ResponseEntity<?> deleteConversationById(@PathVariable Long id) {
        conversationService.deleteConversationById(id);
        return ok();
    }

    @GetMapping(value = "/conversations", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "list All Conversations",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = ConversationResponse.class)))
            }
    )
    public ResponseEntity<?> listAllConversations() {
        LOG.debug("Start of listAllConversations method.");
        List<ConversationResponse> commentResponse = conversationService.listAllConversations();
        LOG.debug("End of listAllConversations method.");
        return ok(commentResponse);
    }

}
