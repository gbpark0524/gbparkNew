package ke.pe.gbpark.response;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public record ExceptionResponse(
        int code,
        String message,
        Map<String, String> target) {
    @Builder
    public ExceptionResponse(int code, String message, Map<String, String> target) {
        this.code = code;
        this.message = message != null ? message : "";
        this.target = target != null ? target : new HashMap<>();
    }

    public void addTarget(String fieldName, String errorMessage) {
        this.target.put(fieldName, errorMessage);
    }
}
