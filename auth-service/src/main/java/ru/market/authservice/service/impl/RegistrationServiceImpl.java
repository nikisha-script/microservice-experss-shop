package ru.market.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.market.authservice.dto.RequestNewClientDto;
import ru.market.authservice.entity.UserProfile;
import ru.market.authservice.exception.RegistrationException;
import ru.market.authservice.mapper.RequestNewClientDtoMapper;
import ru.market.authservice.repository.UserProfileRepository;
import ru.market.authservice.service.RegistrationService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RequestNewClientDtoMapper newClientDtoMapper;
    private final UserProfileRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerNonClient(RequestNewClientDto requestNewClientDto) {
        validationNewClient(requestNewClientDto);
        requestNewClientDto.setPassword(encodePassword(requestNewClientDto.getPassword()));
        UserProfile userProfile = newClientDtoMapper.requestNewClientDtoToUser(requestNewClientDto);
        userProfile.setCreated(LocalDateTime.now());
        userProfile.setUpdated(LocalDateTime.now());
        userProfile.setPushNotification(true);
        repository.save(userProfile);
    }

    private String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private void validationNewClient(RequestNewClientDto requestNewClientDto) {
        Optional<UserProfile> existUser = repository.findByEmail(requestNewClientDto.getLogin());
        String[] checkLogin = requestNewClientDto.getLogin().split("@");
        if (requestNewClientDto.getPassword().length() <= 6) {
            throw new RegistrationException(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                    "Пользователь должен иметь пароль больше 6 символов. ");
        }
        if (checkLogin.length != 2 && checkLogin[1].contains(".")) {
            throw new RegistrationException(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                    "Пользователь должен иметь email по примеру 'admin@mail.ru'");
        }
        if (existUser.isPresent()) {
            throw new RegistrationException(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                    "Пользователь с таким email уже существует. ");
        }
    }
}
