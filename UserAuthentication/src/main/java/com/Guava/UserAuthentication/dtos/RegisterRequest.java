package com.Guava.UserAuthentication.dtos;

import com.Guava.UserAuthentication.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest  {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
