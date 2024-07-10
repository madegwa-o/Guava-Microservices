package com.Guava.UserAuthentication.auth;

import com.Guava.UserAuthentication.dtos.AuthResponse;
import com.Guava.UserAuthentication.dtos.LoginRequest;
import com.Guava.UserAuthentication.dtos.RegisterRequest;
import com.Guava.UserAuthentication.token.JwtTokenService;
import com.Guava.UserAuthentication.user.AppUser;
import com.Guava.UserAuthentication.user.CustomUserDetails;
import com.Guava.UserAuthentication.user.CustomUserDetailsService;
import com.Guava.UserAuthentication.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request){
        try {
            AppUser user = AppUser.builder()
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            userService.saveUser(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // Load user details
            CustomUserDetails userDetails =
                    (CustomUserDetails) customUserDetailsService.loadUserByUsername(loginRequest.getUsername());

            // Set security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate token
            String token = jwtTokenService.genereteToken(userDetails);

            // Create response
            AuthResponse response = new AuthResponse(token, userDetails);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
