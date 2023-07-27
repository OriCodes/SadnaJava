package com.forums.forum.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Already follow the other")
public class AlreadyFollowsException extends  Exception{
}
