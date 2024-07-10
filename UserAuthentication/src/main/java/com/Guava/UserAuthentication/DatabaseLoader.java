package com.Guava.UserAuthentication;

import com.Guava.UserAuthentication.user.AppUser;
import com.Guava.UserAuthentication.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseLoader {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Bean
    public CommandLineRunner databaseInit(){
        return args -> {
            String hashed_password = passwordEncoder.encode("1234");
            AppUser user = new AppUser("john","doe","john@gmail.com",hashed_password);
            userRepo.save(user);
        };
    }
}
