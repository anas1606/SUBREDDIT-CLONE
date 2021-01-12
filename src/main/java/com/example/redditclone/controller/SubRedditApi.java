package com.example.redditclone.controller;

import com.example.redditclone.dto.SubRedditdto;
import com.example.redditclone.service.SubRedditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
public class SubRedditApi {
    @Autowired
    private  SubRedditService subredditservice;

    @PostMapping
    public ResponseEntity<SubRedditdto> createSubreddit(@RequestBody SubRedditdto subredditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditservice.save(subredditDto));
    }

    @GetMapping("/getall")
    public List<SubRedditdto> getAllSubreddit(){
        return subredditservice.getallsubreddit();
    }

    @GetMapping("/getbyuser")
    public List<SubRedditdto> getbyuser(){
        return subredditservice.getbyuser();
    }
}
