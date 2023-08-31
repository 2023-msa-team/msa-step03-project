package com.example.catalogservice._core.errors.exception;
import org.springframework.http.HttpStatus;

import com.example.catalogservice._core.utils.ApiUtils;

import lombok.Getter;

@Getter
public class Exception403 extends RuntimeException {
    public Exception403(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.FORBIDDEN);
    }

    public HttpStatus status(){
        return HttpStatus.FORBIDDEN;
    }
}