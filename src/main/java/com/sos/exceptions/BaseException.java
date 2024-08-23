package com.sos.exceptions;

import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BaseException extends RuntimeException {

    private final HttpStatus status;
    private final List<String> reasons;

    public BaseException(HttpStatus status, String reasons) {
        this.status = status;
        this.reasons = List.of(reasons);
    }

    public BaseException(HttpStatus status, List<ObjectError> allErrors) {
        this.status = status;
        this.reasons = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}
