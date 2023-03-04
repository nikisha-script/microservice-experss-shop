package ru.gateway.api.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import ru.gateway.api.config.security.jwt.JwtService;
import ru.gateway.api.config.security.util.JWT;
import ru.gateway.api.dto.JwtDto;
import ru.gateway.api.dto.LoginDto;
import ru.gateway.api.exception.InvalidJwtException;
import ru.gateway.api.openfeign.authservice.AuthenticationClient;
import ru.gateway.api.service.AuthenticationService;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationClient authenticationClient;
    private final JwtService jwtService;

    @Override
    @CircuitBreaker
    public JwtDto login(LoginDto loginDto) {
        UUID uuid = authenticationClient.authenticateUser(loginDto).getBody();
        return generateTokens(Objects.requireNonNull(uuid).toString());
    }
    @Override
    public JwtDto generateNewToken(HttpServletRequest request) {
        String jwt = request.getHeader(JWT.HEADER.getValue());
        if (jwt == null) {
            throw new RuntimeException("Refresh token is missing");
        }
        String clientId = getClientIdFromJwt(jwt);
        JwtDto tokens = new JwtDto();
        tokens.setAccessToken(jwtService.generateAccessToken(clientId));
        tokens.setRefreshToken(jwt);
        return tokens;

    }

    private JwtDto generateTokens(String clientId) {
        JwtDto tokens = new JwtDto();
        tokens.setAccessToken(jwtService.generateAccessToken(clientId));
        tokens.setRefreshToken(jwtService.generateRefreshToken(clientId));
        return tokens;
    }

    private String getClientIdFromJwt(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(JWT.KEY.getValue().getBytes(StandardCharsets.UTF_8));
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return String.valueOf(claims.get(JWT.UUID.getValue()));
        } catch (IllegalArgumentException | MalformedJwtException e) {
            throw new InvalidJwtException();
        }
    }

}
