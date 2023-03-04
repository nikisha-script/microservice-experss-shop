package ru.gateway.api.openfeign.authservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gateway.api.dto.LoginDto;

import java.util.UUID;

/**
 * <a href="http://localhost:8761/eureka/apps/auth-service">...</a>
 */
@FeignClient("AUTH-SERVICE/login")
public interface AuthenticationClient {

    @PostMapping()
    ResponseEntity<UUID> authenticateUser(LoginDto loginDto);

}
