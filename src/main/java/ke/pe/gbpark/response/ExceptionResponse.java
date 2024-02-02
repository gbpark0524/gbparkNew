package ke.pe.gbpark.response;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public record ExceptionResponse(
        String code,
        String message,
        Map<String, String> target) {
    @Builder
    public ExceptionResponse(String code, String message, Map<String, String> target) {
        if (code == null) {
            throw new IllegalArgumentException("code must not be null");
        }
        this.code = code;
        this.message = message != null ? message : "";
        this.target = target != null ? target : new HashMap<>();
    }

    public void addTarget(String fieldName, String errorMessage) {
        this.target.put(fieldName, errorMessage);
    }
}
