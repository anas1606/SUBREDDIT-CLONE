package com.example.redditclone.controller;

import com.example.redditclone.dto.Votedto;
import com.example.redditclone.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/vote")
@RestController
public class VoteApi {
    @Autowired
    private VoteService voteservice;

    @GetMapping
    public String test(){
        return voteservice.test();
    }

    @PostMapping("/save")
    public ResponseEntity<Votedto> save(@RequestBody Votedto dto){
        return voteservice.save(dto);
    }

    @GetMapping("/getall")
    public  ResponseEntity<List<Votedto>> getall (){
        return voteservice.getall();
    }

    @GetMapping("/getbypost/{postid}")
    public ResponseEntity<List<Votedto>> getbypost(@PathVariable("postid") Long id){
        return voteservice.getbypost(id);
    }
}
