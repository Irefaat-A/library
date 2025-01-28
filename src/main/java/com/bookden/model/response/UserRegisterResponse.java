package com.bookden.model.response;

import com.bookden.model.request.RegisterUserRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties
public class UserRegisterResponse {
    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotEmpty
    private String role;

    public UserRegisterResponse(RegisterUserRequest registerUserRequest){
        name = registerUserRequest.getName();
        email = registerUserRequest.getEmail();
        role = registerUserRequest.getRole();
    }
}
