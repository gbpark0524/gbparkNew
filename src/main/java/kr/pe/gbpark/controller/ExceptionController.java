package kr.pe.gbpark.controller;

import kr.pe.gbpark.exception.GbparkException;
import kr.pe.gbpark.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Invalid parameter")
                .target(fieldErrors.stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                error -> ofNullable(error.getDefaultMessage())
                                        .orElse("Invalid value"),
                                (msg1, msg2) -> msg1 + "; " + msg2
                        )))
                .build();
    }

    @ResponseBody
    @ExceptionHandler(GbparkException.class)
    public ResponseEntity<ExceptionResponse> gbparkExceptionHandler(GbparkException e) {
        HttpStatus statusCode = e.statusCode();
        ExceptionResponse body = ExceptionResponse.builder()
                .code(statusCode.value())
                .message(e.getMessage())
                .target(e.getTarget())
                .build();

        return ResponseEntity.status(statusCode).body(body);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exception(Exception e) {
        ExceptionResponse body = ExceptionResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
