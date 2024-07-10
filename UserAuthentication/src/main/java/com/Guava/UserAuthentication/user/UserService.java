package com.Guava.UserAuthentication.user;

import com.Guava.UserAuthentication.dtos.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public AppUser findUserByEmail( String email){
        return userRepo.findByEmail( email);
    }

    public AppUser saveUser(AppUser user) {
        return userRepo.save(user);
    }
}
