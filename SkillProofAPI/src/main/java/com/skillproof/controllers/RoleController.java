//package com.skillproof.skillproofapi.controllers;
//
//import com.skillproof.skillproofapi.constants.SwaggerConstants;
//import com.skillproof.skillproofapi.model.request.role.CreateRoleRequest;
//import com.skillproof.skillproofapi.model.request.role.RoleResponse;
//import com.skillproof.skillproofapi.model.request.role.UpdateRoleRequest;
//import com.skillproof.skillproofapi.model.request.skillsAndExperience.CreateSkillsAndExperienceRequest;
//import com.skillproof.skillproofapi.model.request.skillsAndExperience.SkillsAndExperienceResponse;
//import com.skillproof.skillproofapi.services.role.RoleService;
//import com.skillproof.skillproofapi.services.role.RoleServiceImpl;
//import com.skillproof.skillproofapi.services.userRole.UserRoleService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.AllArgsConstructor;
//import org.apache.commons.collections4.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@Tag(name = "User", description = "Manages Roles in skillProof App")
//@Transactional
//public class RoleController extends AbstractController {
//
//    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
//
//    private final RoleService roleService;
//    private final UserRoleService userRoleService;
//
//    public RoleController(RoleService roleService, UserRoleService userRoleService){
//        this.roleService = roleService;
//        this.userRoleService = userRoleService;
//    }
//
//    @PostMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Create roles",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE,
//                            content = @Content(schema = @Schema(implementation = RoleResponse.class)))
//            }
//    )
//    public ResponseEntity<RoleResponse> createRole(@RequestBody @Valid CreateRoleRequest createRoleRequest){
//        RoleResponse roleResponse = roleService.createRole(createRoleRequest);
//        return created(roleResponse);
//    }
//
//    @GetMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Get role by id",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = RoleResponse.class)))
//            }
//    )
//    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id){
//        RoleResponse roleResponse = roleService.getRoleById(id);
//        return ok(roleResponse);
//    }
//
//    @PatchMapping(value = "/roles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Update role by id",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_UPDATE,
//                            content = @Content(schema = @Schema(implementation = RoleResponse.class)))
//            }
//    )
//    public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id,
//                                                   @RequestBody @Valid UpdateRoleRequest updateRoleRequest){
//        RoleResponse roleResponse = roleService.updateRole(id, updateRoleRequest);
//        return ok(roleResponse);
//    }
//
//    @GetMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "List All roles",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_GET,
//                            content = @Content(schema = @Schema(implementation = RoleResponse.class)))
//            }
//    )
//    public ResponseEntity<List<RoleResponse>> listAllRoles(){
//        List<RoleResponse> roleResponse = roleService.listAllRoles();
//        if (CollectionUtils.isEmpty(roleResponse)){
//            return noContent();
//        }
//        return ok(roleResponse);
//    }
//
//    @PostMapping(value = "/roles/{roleId}/users/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Grant user to role",
//            responses = {
//                    @ApiResponse(description = SwaggerConstants.SUCCESS,
//                            responseCode = SwaggerConstants.SUCCESS_RESPONSE_CODE_CREATE)
//            }
//    )
//    public ResponseEntity<String> grantUserToRole(@PathVariable Long roleId, @PathVariable String userName){
//        userRoleService.grantUserToRole(userName, roleId);
//        return ok();
//    }
//
//}
