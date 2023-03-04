package ru.market.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.market.authservice.dto.RequestNewClientDto;
import ru.market.authservice.service.RegistrationService;

@RestController
@RequestMapping(RegistrationController.REGISTRATION_URL)
@RequiredArgsConstructor
public class RegistrationController {

    public static final String REGISTRATION_URL = "/registration";

    private final RegistrationService registrationService;

    @PostMapping("user-profile/new")
    public ResponseEntity<Void> registerNonClient(@RequestBody RequestNewClientDto requestNewClientDto) {
        registrationService.registerNonClient(requestNewClientDto);
        return ResponseEntity.ok().build();
    }

}
