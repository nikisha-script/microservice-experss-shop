package ru.market.authservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Before(value = "execution(* ru.market.authservice.service.impl.AuthenticationServiceImpl.*(..))")
    public void beforeAdviceForAuthenticationService(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName() + " method invoked");
    }

    @Before(value = "execution(* ru.market.authservice.service.impl.RegistrationServiceImpl.*(..))")
    public void beforeAdviceForRegistrationService(JoinPoint joinPoint) {
        log.info(joinPoint.getSignature().getName() + " method invoked");
    }

}
