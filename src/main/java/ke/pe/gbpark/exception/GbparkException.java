package ke.pe.gbpark.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GbparkException extends RuntimeException {
    public GbparkException(String message) {super(message);}
    public abstract HttpStatus statusCode();
}
