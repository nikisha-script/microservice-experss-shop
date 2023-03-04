package ru.gateway.api.openfeign.authservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gateway.api.dto.RequestNewClientDto;

/**
 * <a href="http://localhost:8761/eureka/apps/auth-service">...</a>
 */
@FeignClient("AUTH-SERVICE/registration")
public interface RegistrationClient {

    @PostMapping("user-profile/new")
    ResponseEntity<Void> registerNonClient(RequestNewClientDto requestClientDto);

}
