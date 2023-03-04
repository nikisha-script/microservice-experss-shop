package ru.market.authservice.service;

import ru.market.authservice.dto.RequestNewClientDto;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {

    @Transactional
    void registerNonClient(RequestNewClientDto requestNewClientDto);

}
