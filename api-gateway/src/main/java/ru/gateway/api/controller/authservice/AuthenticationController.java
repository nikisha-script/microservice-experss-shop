package ru.gateway.api.controller.authservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gateway.api.dto.JwtDto;
import ru.gateway.api.dto.LoginDto;
import ru.gateway.api.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationController.URL_LOGIN)
@Tag(name = "Authentication Controller", description = "Manage client's authentication")
public class AuthenticationController {

    public static final String URL_LOGIN = "/api/v1/login";

    private final AuthenticationService authenticationService;

    @Operation(summary = "Authenticate user", description = "Verify password and return tokens")
    @ApiResponse(responseCode = "200", description = "User has been authenticated", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = JwtDto.class))})
    @ApiResponse(responseCode = "400", description = "Wrong credentials", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping()
    public ResponseEntity<JwtDto> authenticateUser(
            @RequestBody @Parameter(description = "login and password") LoginDto loginDto) {
        JwtDto tokens = authenticationService.login(loginDto);
        return ResponseEntity.ok(tokens);
    }

    @Operation(summary = "Refresh token", description = "Return new generate access token")
    @ApiResponse(responseCode = "200", description = "New token generated", content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = JwtDto.class))})
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("/token")
    public ResponseEntity<JwtDto> refreshToken(HttpServletRequest request) {

        JwtDto tokens = authenticationService.generateNewToken(request);
        return ResponseEntity.ok(tokens);
    }


}
