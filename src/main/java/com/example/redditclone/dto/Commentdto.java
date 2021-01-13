package com.example.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commentdto {
    private Long id;
    private Long postid;
    private Instant createddate;
    private String text;
    private String username;
}
