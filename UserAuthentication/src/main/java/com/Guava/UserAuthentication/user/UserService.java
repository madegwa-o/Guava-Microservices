package com.Guava.UserAuthentication.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public AppUser findUserByEmail( String email){
        return userRepo.findByEmail( email);
    }

}
