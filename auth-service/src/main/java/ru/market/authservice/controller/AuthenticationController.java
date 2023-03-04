package ru.market.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.market.authservice.dto.LoginDto;
import ru.market.authservice.service.AuthenticationService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationController.AUTHENTICATION_URL)
public class AuthenticationController {
    public static final String AUTHENTICATION_URL = "/login";

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<UUID> authenticateUser(@RequestBody LoginDto loginDto) {
        UUID clientId = authenticationService.login(loginDto);
        return ResponseEntity.ok(clientId);
    }


}
