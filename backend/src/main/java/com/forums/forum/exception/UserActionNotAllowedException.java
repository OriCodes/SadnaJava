package com.forums.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserActionNotAllowedException extends Exception{
    public UserActionNotAllowedException(String message) {
        super(message);
    }
}
