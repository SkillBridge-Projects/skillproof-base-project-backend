//package com.skillproof.skillproofapi.controllers;
//
//
//import com.skillproof.skillproofapi.constants.SwaggerConstants;
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.model.request.user.UserResponse;
//import com.skillproof.skillproofapi.services.admin.AdminService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.AllArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@AllArgsConstructor
//@Tag(name = "Admin", description = "Admin activities for all the users")
//public class AdminController {
//
//    private final AdminService adminService;
//
//    @CrossOrigin(origins = "*") //CrossOrigin: For connecting with angular
//    @GetMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "List All users.",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_LIST,
//                            content = @Content(schema = @Schema(implementation = UserResponse.class)))
//            }
//    )
//    public List<UserResponse> listAllUsers() {
//        return adminService.listUsers();
//    }
//
//
//}
