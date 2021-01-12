package com.example.redditclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Logindto {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
