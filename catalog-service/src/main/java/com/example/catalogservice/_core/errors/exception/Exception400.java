package com.example.catalogservice._core.errors.exception;

import org.springframework.http.HttpStatus;

import com.example.catalogservice._core.utils.ApiUtils;

import lombok.Getter;

@Getter
public class Exception400 extends RuntimeException {

    public Exception400(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.BAD_REQUEST);
    }

    public HttpStatus status(){
        return HttpStatus.BAD_REQUEST;
    }
}