package com.jeanboy.web.demo.exceptions;


import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.exceptions.info.ErrorInfo;
import com.jeanboy.web.demo.exceptions.info.ExceptionInfo;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class ServerException extends RuntimeException {

    private ExceptionInfo exception;

    public ServerException(ErrorCode code) {
        super(code.getReasonPhrase());
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (code.is400Error()) {
            status = HttpStatus.BAD_REQUEST;
        } else if (code.is401Error()) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (code.is403Error()) {
            status = HttpStatus.FORBIDDEN;
        }
        exception = new ExceptionInfo(status.value(), status.getReasonPhrase());
        addError(code);
    }

    public ServerException(HttpStatus status) {
        super(status.getReasonPhrase());
        exception = new ExceptionInfo(status.value(), status.getReasonPhrase());
    }

    public ServerException(HttpStatus status, String message) {
        super(message);
        exception = new ExceptionInfo(status.value(), status.getReasonPhrase());
    }


    public ServerException addError(ErrorCode code) {
        if (exception.getErrors() == null) {
            exception.setErrors(new ArrayList<>());
        }
        exception.getErrors().add(new ErrorInfo(code.value(), code.getReasonPhrase()));
        return this;
    }

    public ServerException addError(ErrorInfo error) {
        if (exception.getErrors() == null) {
            exception.setErrors(new ArrayList<>());
        }
        exception.getErrors().add(error);
        return this;
    }

    public ExceptionInfo getException() {
        return exception;
    }
}
