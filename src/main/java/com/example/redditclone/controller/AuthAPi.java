package com.example.redditclone.controller;
import com.example.redditclone.dto.Logindto;
import com.example.redditclone.dto.UserDto;
import com.example.redditclone.service.SignupAndAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthAPi {

    @Autowired
    private SignupAndAuth signupandpauth;

    @GetMapping("/hello")
    public String test(){
        return "hhh";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserDto userdto){
        signupandpauth.signup(userdto);
    }

    @GetMapping("/auth/{token}")
    public String varifytoken(@PathVariable("token") String token){
        return signupandpauth.verifytoken(token);
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Logindto dto){
        return  signupandpauth.login(dto);
    }
}
