package ru.gateway.api.exception.handler;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import ru.gateway.api.exception.InvalidJwtException;
import ru.gateway.api.exception.NotEqualClientIdException;
import ru.gateway.api.exception.dto.ErrorDto;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> feignExceptionHandler(FeignException exception) {
        log.error("Internal server error. " + exception.getMessage());
        ResponseEntity.BodyBuilder responseBuilder = getDefaultResponseEntityBuilder(exception.status());
        Optional<ByteBuffer> body = exception.responseBody();
        if (body.isPresent()) {
            String message = getDecodedResponseBody(body.get());
            return responseBuilder.body(message);
        }
        return responseBuilder.build();
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> serverExceptionHandler(Exception e) {
        log.error("Internal server error. " + e.getMessage());
        return createResponseEntity(
                HttpStatus.INTERNAL_SERVER_ERROR,
                new ErrorDto(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        e.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> httpMessageNotReadableExceptionHandler(Exception e) {
        log.error("Http Message Not Readable Exception. " + e.getMessage());
        return createResponseEntity(
                HttpStatus.BAD_REQUEST,
                new ErrorDto(Integer.toString(HttpStatus.BAD_REQUEST.value()),
                        "JSON parse error"));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDto> maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        log.error("Max upload size exceeded. " + e.getMessage());
        return createResponseEntity(HttpStatus.BAD_REQUEST,
                new ErrorDto(Integer.toString(HttpStatus.BAD_REQUEST.value()), e.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorDto> invalidJwtExceptionHandler(InvalidJwtException e) {
        log.error("Jwt is invalid. " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotEqualClientIdException.class)
    public ResponseEntity<ErrorDto> notEqualClientIdExceptionHandler(NotEqualClientIdException e) {
        log.error("Authorized client id not equals with input data. " + e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private ResponseEntity<ErrorDto> createResponseEntity(HttpStatus status, ErrorDto errorDto) {
        return ResponseEntity.status(status)
                .header("Content-Type", "application/json")
                .body(errorDto);
    }

    private ResponseEntity.BodyBuilder getDefaultResponseEntityBuilder(int status) {
        return ResponseEntity.status(HttpStatus.valueOf(status)).contentType(MediaType.APPLICATION_JSON);
    }

    private String getDecodedResponseBody(ByteBuffer byteBuffer) {
        return StandardCharsets.UTF_8.decode(byteBuffer).toString();
    }

}
