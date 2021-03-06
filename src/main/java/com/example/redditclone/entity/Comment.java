package com.example.redditclone.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import static javax.persistence.FetchType.LAZY;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String text;
    @ManyToOne(fetch = LAZY)
    private Post post;
    private Instant createddate;
    @ManyToOne(fetch = LAZY)
    private User user;
}
