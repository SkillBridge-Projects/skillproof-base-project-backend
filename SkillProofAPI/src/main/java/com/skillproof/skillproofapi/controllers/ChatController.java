package com.skillproof.skillproofapi.controllers;

import com.skillproof.skillproofapi.constants.SwaggerConstants;
import com.skillproof.skillproofapi.model.entity.Chat;
import com.skillproof.skillproofapi.model.entity.Message;
import com.skillproof.skillproofapi.services.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@Tag(name = "Chat", description = "Manages chats of users in skillProof App")
public class ChatController {

    private final ChatService chatService;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{userId}/chats", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List All Chats",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
                            content = @Content(schema = @Schema(implementation = Chat.class)))
            }
    )
    public Set<Chat> getAllChats(@PathVariable Long userId) {
        return chatService.getAllChats(userId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/in/{userId}/chat/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List Chat for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
                            content = @Content(schema = @Schema(implementation = Chat.class)))
            }
    )
    public Chat getChatByUser(@PathVariable Long userId, @PathVariable Long chatId) {
        return chatService.getChatByUser(userId, chatId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/in/{userId}/chat/{chatId}/new-message", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add New Message for a Chat for User",
            responses = {
                    @ApiResponse(description = SwaggerConstants.SUCCESS,
                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
                            content = @Content(schema = @Schema(implementation = Chat.class)))
            }
    )
    public ResponseEntity<?> newMessage(@PathVariable Long userId, @PathVariable Long chatId, @RequestBody Message message) {
        return ResponseEntity.ok(chatService.newMessage(userId, chatId, message));
    }

}


