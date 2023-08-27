package com.forums.forum.auth;

import com.forums.forum.exception.UserNameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserNameAlreadyExistException() {
        // No need to add any code here, just the annotation is enough
    }
}

