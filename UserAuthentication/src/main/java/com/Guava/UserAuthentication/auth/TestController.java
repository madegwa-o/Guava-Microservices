package com.Guava.UserAuthentication.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/hello")
    public String greet(){
        return "hello work :)";
    }

    @GetMapping("/bye")
    public String bye(){
        return "i'll see you later :)";
    }

}
