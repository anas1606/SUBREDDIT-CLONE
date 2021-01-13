package com.example.redditclone.controller;

import com.example.redditclone.dto.Commentdto;
import com.example.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/api/comment")
@RestController
@AllArgsConstructor
public class CommentApi {
    private final CommentService commentservice;

    @PostMapping("/save")
    public ResponseEntity<Void> createComment(@RequestBody Commentdto commentsDto) {
        commentservice.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/bypost/{postid}")
    public ResponseEntity<List<Commentdto>> getAllCommentsForPost(@PathVariable("postid") Long postid) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentservice.getAllCommentsForPost(postid));
    }

    @GetMapping("/byuser/{username}")
    public ResponseEntity<List<Commentdto>> getAllCommentsForUser(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentservice.getAllCommentsForUser(username));
    }
}
