package kr.pe.gbpark.exception;

import org.springframework.http.HttpStatus;

public class InvalidPassword extends GbparkException{
    private static final String MESSAGE = "wrong password";

    public InvalidPassword() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.FORBIDDEN;
    }
}
