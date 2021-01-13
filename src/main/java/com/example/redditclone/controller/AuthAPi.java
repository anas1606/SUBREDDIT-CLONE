package com.example.redditclone.controller;
import com.example.redditclone.dto.Logindto;
import com.example.redditclone.dto.UserDto;
import com.example.redditclone.service.SignupAndAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
@Slf4j
@Validated
public class AuthAPi {

    @Autowired
    private SignupAndAuth signupandpauth;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userdto){
         return status(HttpStatus.OK).body(signupandpauth.signup(userdto));
    }

    @GetMapping("/auth/{token}")
    public ResponseEntity<String> varifytoken(@PathVariable("token") String token){
        return signupandpauth.verifytoken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Logindto dto){
        return  signupandpauth.login(dto);
    }
}
