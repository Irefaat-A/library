package com.bookden.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class ApiSuccessfulResponse extends ApiResponse{
    private Object data;
    private String message;

    public ApiSuccessfulResponse(Object data, String... message) {
        this.setStatus(HttpStatus.OK);
        this.data = data;
        this.message = message.length > 0? message[0]: "";
    }
}
