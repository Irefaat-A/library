package com.bookden.model.request;

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
public class RegisterUserRequest {
    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotEmpty
    private String role;

    @NotEmpty
    private String password;
}
