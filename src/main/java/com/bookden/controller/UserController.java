package com.bookden.controller;

import com.bookden.entity.UserInfo;
import com.bookden.model.request.AuthRequest;
import com.bookden.model.request.RegisterUserRequest;
import com.bookden.model.response.ApiResponse;
import com.bookden.model.response.ApiSuccessfulResponse;
import com.bookden.model.response.UserRegisterResponse;
import com.bookden.security.JwtService;
import com.bookden.service.UserInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("bookden/v1/auth")
public class UserController {
    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    UserController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        String response = service.addUser(registerUserRequest);
        return ResponseEntity.ok(new ApiSuccessfulResponse(new UserRegisterResponse(registerUserRequest),response));
    }

    @PostMapping("/generateToken")
    public ResponseEntity<ApiResponse> authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new ApiSuccessfulResponse(token, "Success"));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}
