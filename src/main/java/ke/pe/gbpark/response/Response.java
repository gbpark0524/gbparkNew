package ke.pe.gbpark.response;

public record Response<T>(boolean success, String message, T data) {}