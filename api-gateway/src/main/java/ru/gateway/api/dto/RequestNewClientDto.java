package ru.gateway.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PUBLIC)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "DTO incoming data of a registering client")
public class RequestNewClientDto {

    @Schema(description = "client's email")
    private String login;
    @Schema(description = "client's password")
    private String password;

}
