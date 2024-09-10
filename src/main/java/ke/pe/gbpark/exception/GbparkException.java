package ke.pe.gbpark.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class GbparkException extends RuntimeException {
    final Logger logger = LoggerFactory.getLogger(GbparkException.class.getName());
    public GbparkException(String message) {super(message);}
    public abstract HttpStatus statusCode();
    public final Map<String, String> target = new HashMap<>();
    public void addTarget(String fieldName, String message) {
        target.put(fieldName, message);
    }
}
