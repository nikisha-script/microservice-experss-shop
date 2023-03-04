package ru.market.authservice.service;

import ru.market.authservice.dto.LoginDto;

import java.util.UUID;

public interface AuthenticationService {

    UUID login(LoginDto dto);

}
