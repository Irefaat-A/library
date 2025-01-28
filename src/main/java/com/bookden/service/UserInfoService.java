package com.bookden.service;

import com.bookden.entity.UserInfo;
import com.bookden.model.UserInfoDetails;
import com.bookden.model.request.RegisterUserRequest;
import com.bookden.repository.UserInformationRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService{

    private final UserInformationRepository repository;
    private final PasswordEncoder encoder;
    private String loggedInUsername;

    public UserInfoService(UserInformationRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder =  encoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByName(username);
        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public String addUser(RegisterUserRequest registerUserRequest) {
        UserInfo userInfo = new UserInfo(registerUserRequest.getName(), registerUserRequest.getEmail(), registerUserRequest.getPassword(), registerUserRequest.getRole());
        userInfo.setPassword(encoder.encode(registerUserRequest.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }

    public static String getLoggedInUsername(){
        return extractUsernameContext();
    }

    private static String extractUsernameContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoDetails details = (UserInfoDetails) authentication.getPrincipal();
        return details.getUsername();
    }

}
