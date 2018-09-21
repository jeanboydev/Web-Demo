package com.jeanboy.web.demo.exceptions;


import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.constants.HttpStatus;
import com.jeanboy.web.demo.exceptions.info.ErrorInfo;
import com.jeanboy.web.demo.exceptions.info.ExceptionInfo;

import java.util.ArrayList;

public class ServerException extends RuntimeException {

    private ExceptionInfo exception;

    public ServerException(int status){
        super(HttpStatus.getMessage(status));
        exception = new ExceptionInfo(status, HttpStatus.getMessage(status));
    }

    public ServerException(int status, String message){
        super(message);
        exception = new ExceptionInfo(status, HttpStatus.getMessage(status));
    }


    public ServerException addError(long code){
        if(exception.getErrors() == null){
            exception.setErrors(new ArrayList<>());
        }
        exception.getErrors().add(new ErrorInfo(code, ErrorCode.getMessage(code)));
        return this;
    }

    public ServerException addError(ErrorInfo error){
        if(exception.getErrors() == null){
            exception.setErrors(new ArrayList<>());
        }
        exception.getErrors().add(error);
        return this;
    }

    public ExceptionInfo getException() {
        return exception;
    }
}
