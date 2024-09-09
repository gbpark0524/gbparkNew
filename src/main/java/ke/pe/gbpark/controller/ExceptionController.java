package ke.pe.gbpark.controller;

import ke.pe.gbpark.exception.GbparkException;
import ke.pe.gbpark.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        ExceptionResponse response = ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Invalid parameter")
                .build();
        for (FieldError fieldError : fieldErrors) {
            response.addTarget(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
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
}
