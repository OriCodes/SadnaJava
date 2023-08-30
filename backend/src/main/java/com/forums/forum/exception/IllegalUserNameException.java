package com.forums.forum.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class IllegalUserNameException extends  Exception{
    public IllegalUserNameException(String message) {
        super(message);
    }
}
