package ke.pe.gbpark.response;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionResponseTest {
    @Test
    public void exceptionValidSuccess() {
        ExceptionResponse response = ExceptionResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("it's wrong value")
                .build();

        assertNotNull(response);
    }

    @Test
    public void exceptionValidTest() {
        assertThrows(IllegalArgumentException.class, () ->
                ExceptionResponse.builder()
                .message("it's wrong value")
                .build());
    }
}