package kr.pe.gbpark.response;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExceptionResponseTest {
    @Test
    public void exceptionValidSuccess() {
        ExceptionResponse response = ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("it's wrong value")
                .build();

        assertNotNull(response);
    }
}