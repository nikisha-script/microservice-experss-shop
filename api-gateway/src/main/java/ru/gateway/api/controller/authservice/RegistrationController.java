package ru.gateway.api.controller.authservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gateway.api.dto.RequestNewClientDto;
import ru.gateway.api.exception.dto.ErrorDto;
import ru.gateway.api.openfeign.authservice.RegistrationClient;

@RestController
@RequestMapping(RegistrationController.URL_REGISTRATION)
@RequiredArgsConstructor
@Tag(name = "Registration Controller", description = "Manage client's registration")
public class RegistrationController {

    public static final String URL_REGISTRATION = "/api/v1/registration";

    private final RegistrationClient registrationClient;

    @Operation(summary = "Register non-existent client", description = "Save client into application's profiles DB")
    @ApiResponse(responseCode = "200", description = "User has been successfully registered",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RequestNewClientDto.class))})
    @ApiResponse(responseCode = "400", description = "Client with email already exists", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))})
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping("/user-profile/new")
    public ResponseEntity<Void> registerNonClient(
            @RequestBody @Parameter(description = "client data") RequestNewClientDto requestClientDto) {
        return registrationClient.registerNonClient(requestClientDto);
    }

}
