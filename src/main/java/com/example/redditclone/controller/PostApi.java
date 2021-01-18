package com.example.redditclone.controller;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.dto.SubRedditdto;
import com.example.redditclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/post")
@RestController
public class PostApi {
    @Autowired
    private PostService postservice;

    @PostMapping("/save")
    public ResponseEntity<PostRequest> createPost(@RequestBody PostRequest postRequest) {
        return new ResponseEntity<>(postservice.save(postRequest),HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return postservice.getallposts();
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<PostResponse> getallbyid(@PathVariable("id") Long id){
        return  postservice.getallbyid(id);
    }

    @GetMapping("/bysubreddit/{subreddit}")
    public ResponseEntity<List<PostResponse>> getbysubreddit(@PathVariable("subreddit") String subredditname){
        return postservice.getbysubreddit(subredditname);
    }

    @GetMapping("/byuser")
    public ResponseEntity<List<PostResponse>> getbyuser(){
        return postservice.getbyuser();
    }
}
