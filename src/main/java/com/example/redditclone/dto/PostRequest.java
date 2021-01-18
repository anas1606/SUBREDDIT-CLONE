package com.example.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequest {
    private Long postid;
    private String subredditname;
    private String postname;
    private String url;
    private String description;
}
