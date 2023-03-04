package ru.market.authservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.market.authservice.exception.RegistrationException;
import ru.market.authservice.exception.WrongCredentialsException;

@Component
@Aspect
@Slf4j
public class ThrowingAspect {

    @AfterThrowing(value = "execution(* ru.market.authservice.service.impl.AuthenticationServiceImpl.*(..))", throwing = "ex")
    public void afterThrowingForAuthenticationService(Exception ex) {
        log.error("Internal server error. " + ex.getMessage());
    }

    @AfterThrowing(value = "execution(* ru.market.authservice.service.impl.AuthenticationServiceImpl.*(..))", throwing = "ex")
    public void afterThrowingForAuthenticationServiceWithWrongCredentialsException(WrongCredentialsException ex) {
        log.error("Wrong credentials. " + ex.getErrorMessage());
    }

    @AfterThrowing(value = "execution(* ru.market.authservice.service.impl.RegistrationServiceImpl.*(..))", throwing = "ex")
    public void afterThrowingForRegistrationServiceWithExistAccount(RegistrationException ex) {
        log.error("Client with email already exists. " + ex.getErrorMessage());
    }

}
