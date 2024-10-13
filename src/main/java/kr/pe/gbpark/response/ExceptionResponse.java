package kr.pe.gbpark.response;

import lombok.Builder;

import java.util.Collections;
import java.util.Map;

public record ExceptionResponse(
        int code,
        String message,
        Map<String, String> target) {
    @Builder
    public ExceptionResponse(int code, String message, Map<String, String> target) {
        this.code = code;
        this.message = message != null ? message : "";
        this.target = target != null ? Map.copyOf(target) : Collections.emptyMap();
    }
}
