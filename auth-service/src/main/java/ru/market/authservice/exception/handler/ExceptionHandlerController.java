package ru.market.authservice.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.market.authservice.exception.RegistrationException;
import ru.market.authservice.exception.WrongCredentialsException;
import ru.market.authservice.exception.dto.ErrorDto;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> exceptionHandler() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorDto> registrationExceptionHandler(RegistrationException e) {
        ErrorDto body = new ErrorDto(e.getErrorCode(), e.getErrorMessage());
        return createResponseEntity(getStatus(e.getErrorCode()), body);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<ErrorDto> wrongCredentialsExceptionHandler(WrongCredentialsException e) {
        ErrorDto body = new ErrorDto(e.getErrorCode(), e.getErrorMessage());
        return createResponseEntity(getStatus(e.getErrorCode()), body);
    }

    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity.status(status)
                .header("Content-Type", "application/json")
                .body(body);
    }

    private HttpStatus getStatus(String errorCode) {
        return HttpStatus.valueOf(Integer.parseInt(errorCode));
    }
}
