package com.Guava.UserAuthentication.dtos;

import com.Guava.UserAuthentication.user.CustomUserDetails;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
public class AuthResponse {

    private final CustomUserDetails customUserDetails;
    @Getter
    @Setter
    private String token;

    public AuthResponse(String token, CustomUserDetails customUserDetails) {

        this.customUserDetails = customUserDetails;
        this.token = token;
    }

    public Long getUserId () {
        return this.customUserDetails.getUserId();
    }

    public String getUsername(){
        return customUserDetails.getUsername();
    }

    public String getFirstName(){
        return this.customUserDetails.getFirstName();
    }

    public String getLastName(){
        return this.customUserDetails.getLastName();
    }

}
