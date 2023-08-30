package com.example.userservice._core.errors.exception;
import org.springframework.http.HttpStatus;

import com.example.userservice._core.utils.ApiUtils;

import lombok.Getter;

@Getter
public class Exception401 extends RuntimeException {
    public Exception401(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.UNAUTHORIZED);
    }

    public HttpStatus status(){
        return HttpStatus.UNAUTHORIZED;
    }
}