package ke.pe.gbpark.response;

import lombok.Builder;

public record ExceptionResponse(
        String code,
        String message,
        String target) {
    @Builder
    public ExceptionResponse(String code, String message, String target) {
        if (code == null) {
            throw new IllegalArgumentException("code must not be null");
        }
        this.code = code;
        this.message = message != null ? message : "";
        this.target = target != null ? target : "";
    }
}
