package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.request.LoginResponseDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.impl.UserHandler;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.configuration.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final IUserPersistencePort userPersistencePort;
    private final UserHandler userHandler;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticate user with email and password"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content)
    })
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
        );
        UserModel user = userPersistencePort.getUserByEmail(loginRequestDto.getEmail());
        String token = jwtUtils.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDto(token));
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
    public ResponseEntity<Void> registerCustomer(@RequestBody UserRequestDto userRequestDto){
        userHandler.saveUser(userRequestDto,"CUSTOMER");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
