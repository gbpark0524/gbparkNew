package ke.pe.gbpark.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class GbparkException extends RuntimeException {
    public GbparkException(String message) {super(message);}
    public abstract HttpStatus statusCode();
    public final Map<String, String> target = new HashMap<>();
    public void addTarget(String fieldName, String message) {
        target.put(fieldName, message);
    }
}
