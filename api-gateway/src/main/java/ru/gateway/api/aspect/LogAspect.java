package ru.gateway.api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Before(value = "execution(* ru.gateway.api.service.impl.AuthenticationServiceImpl.*(..))")
    public void beforeAdviceForAuthenticationService(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName() + " method invoked");
    }

    @Before(value = "execution(* ru.gateway.api.openfeign.authservice.RegistrationClient.*(..))")
    public void beforeAdviceForRegistrationClient(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName() + " method invoked");
    }

}
