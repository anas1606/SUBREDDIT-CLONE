package com.example.redditclone.entity;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import static javax.persistence.FetchType.LAZY;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postid;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postname;
    private String url;
    @Lob
    private String description;
    private Integer votecount = 0;
    @ManyToOne(fetch = LAZY)
    private User user;
    private Instant createddate;
    @ManyToOne(fetch = LAZY)
    private SubReddit subreddit;
}
