package ke.pe.gbpark.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionResponseTest {
    @Test
    public void exceptionValidTest() {
        assertThrows(IllegalArgumentException.class, () ->
                ExceptionResponse.builder()
                .message("잘못된 값입니다.")
                .build());
    }
}