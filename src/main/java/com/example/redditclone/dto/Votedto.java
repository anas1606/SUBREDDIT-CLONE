package com.example.redditclone.dto;

import com.example.redditclone.entity.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Votedto {
    private Long id;
    private VoteType votetype;
    private Long postid;
}
