package com.forums.forum.auth;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.TopicAlreadyExistException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.exception.IllegalUserNameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalUserNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserNameAlreadyExistException() {}

    @ExceptionHandler(UserActionNotAllowedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserActionNotAllowedException() {}

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleResourceNotFoundException() {}

    @ExceptionHandler(TopicAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void TopicAlreadyExistException() {}

}

