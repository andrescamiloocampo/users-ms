package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler userHandler;

    @Operation(
            summary = "Create User by role",
            description = "Allows you to create a user by specifying the role to be assigned" +
                    "The creation rules are as follows:\n" +
                    "- Only an **ADMIN** can create users with the **OWNER** role. \n" +
                    "- Only an **OWNER** can create users with the **EMPLOYEE** role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden, user does not have permission to create with this role", content = @Content)
    })
    @PreAuthorize("(#role == 'OWNER' and hasAuthority('ADMIN')) or (#role == 'EMPLOYEE' and hasAuthority('OWNER'))")
    @PostMapping("/{role}")
    public ResponseEntity<Void> saveUser(@RequestBody UserRequestDto userRequestDto,
                                         @PathVariable String role,
                                         @RequestParam(defaultValue = "0",required = false) int bid,
                                         Authentication authentication) {
        int publisherId = Integer.parseInt(authentication.getPrincipal().toString());
        userHandler.saveUser(userRequestDto, role.toUpperCase(), publisherId, bid);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create Customer",
            description = "No previous authorization required"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<Void> registerCustomer(@RequestBody UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto, "CUSTOMER", 0, 0);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PreAuthorize("hasAnyAuthority('ADMIN','OWNER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) {
        UserResponseDto user = userHandler.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
