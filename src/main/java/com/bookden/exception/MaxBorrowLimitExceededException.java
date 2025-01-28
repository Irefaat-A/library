package com.bookden.exception;

import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MaxBorrowLimitExceededException extends BaseException {
    public MaxBorrowLimitExceededException(int exceededLimit) {
        super(HttpStatus.BAD_REQUEST, "Maximum borrow limit exceed. You are allowed a maximum of " + exceededLimit);
    }
}
