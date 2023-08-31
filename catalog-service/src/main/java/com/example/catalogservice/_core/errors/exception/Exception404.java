package com.example.catalogservice._core.errors.exception;
import org.springframework.http.HttpStatus;

import com.example.catalogservice._core.utils.ApiUtils;

import lombok.Getter;

@Getter
public class Exception404 extends RuntimeException {
    public Exception404(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.NOT_FOUND);
    }

    public HttpStatus status(){
        return HttpStatus.NOT_FOUND;
    }
}