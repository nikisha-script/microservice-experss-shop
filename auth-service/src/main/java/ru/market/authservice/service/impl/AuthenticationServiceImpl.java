package ru.market.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.market.authservice.dto.LoginDto;
import ru.market.authservice.entity.UserProfile;
import ru.market.authservice.exception.WrongCredentialsException;
import ru.market.authservice.repository.UserProfileRepository;
import ru.market.authservice.service.AuthenticationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UUID login(LoginDto dto) {
        String login = dto.getLogin();
        String password = dto.getPassword();
        UserProfile userProfile = getVerifiedUserByEmail(login);
        if (!isPasswordEquals(password, userProfile.getPassword())) {
            throw new WrongCredentialsException(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                    "Пользователь с таким параметры не существует. ");
        }
        return userProfile.getId();
    }

    private UserProfile getVerifiedUserByEmail(String login) {
        return userProfileRepository.findByEmail(login).orElseThrow(() -> {
            throw new WrongCredentialsException(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                    "Пользователь с таким параметры не существует. ");
        });
    }

    private boolean isPasswordEquals(String password, String storedPassword) {
        return passwordEncoder.matches(password, storedPassword);
    }

}
