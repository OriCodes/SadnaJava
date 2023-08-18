package com.forums.forum.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User name already exist")
public class UserNameAlreadyExistException extends  Exception{
}
