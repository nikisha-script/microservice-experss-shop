package ru.gateway.api.service;

import ru.gateway.api.dto.JwtDto;
import ru.gateway.api.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;


public interface AuthenticationService {

    JwtDto login(LoginDto loginDto);

    JwtDto generateNewToken(HttpServletRequest request);
}
