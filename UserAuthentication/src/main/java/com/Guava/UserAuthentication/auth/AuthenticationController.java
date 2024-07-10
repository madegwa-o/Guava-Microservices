package com.Guava.UserAuthentication.auth;

import com.Guava.UserAuthentication.dtos.AuthResponse;
import com.Guava.UserAuthentication.dtos.LoginRequest;
import com.Guava.UserAuthentication.dtos.RegisterRequest;
import com.Guava.UserAuthentication.token.JwtTokenService;
import com.Guava.UserAuthentication.user.CustomUserDetails;
import com.Guava.UserAuthentication.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterRequest request){
        return null;
    }

    @PostMapping("/authenticate")
    public ResponseEntity loginUser(@RequestBody LoginRequest loginRequest){
        //set the authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        //set the user object
        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        //Set security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate token
        String token = jwtTokenService.genereteToken(userDetails);

        //set response
        AuthResponse response = new AuthResponse(token,userDetails);
        return null;
    }

}
