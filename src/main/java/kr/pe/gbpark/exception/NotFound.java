package kr.pe.gbpark.exception;

import org.springframework.http.HttpStatus;

public class NotFound extends GbparkException{
    private static final String MESSAGE = "대상이 존재하지 않습니다";

    public NotFound() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
