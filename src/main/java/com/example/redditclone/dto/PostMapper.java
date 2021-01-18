package com.example.redditclone.dto;

import com.example.redditclone.entity.Post;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.CommentRepo;
import com.example.redditclone.repository.SubRedditRepo;
import com.example.redditclone.repository.UserDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class PostMapper {
    @Autowired
    private UserDetail userdetail;
    @Autowired
    SubRedditRepo subredditrepo;
    @Autowired
    CommentRepo commentrepo;


    public PostResponse maptodto(Post post){

        PostResponse postr = new PostResponse();

        postr.setId(post.getPostid());
        postr.setSubredditName(post.getSubreddit().getName());
        postr.setCommentCount(commentcount(post));
        postr.setDuration(getDuration(post));
        postr.setUpVote(false);
        postr.setDownVote(false);
        postr.setDescription(post.getDescription());
        postr.setPostName(post.getPostname());
        postr.setUserName(post.getUser().getUsername());
        postr.setUrl(post.getUrl());

        return postr;
    }
    public Post map(PostRequest postRequest){
        Post post = new Post();

        post.setPostname(postRequest.getPostname());
        post.setDescription(postRequest.getDescription());
        post.setCreateddate(Instant.now());
        post.setPostid(postRequest.getPostid());
        post.setUrl(postRequest.getUrl());
        post.setUser(userdetail.findByUsername(new JWTProvider().getcurrentuser()));
        post.setSubreddit(subredditrepo.findByName(postRequest.getSubredditname()));
        post.setVotecount(0);

        return post;
    }
    private int commentcount(Post post){
        return (commentrepo.findByPost(post)).size();
    }
    private String getDuration(Post post) {
        return TimeAgo.using(post.getCreateddate().toEpochMilli());
    }
}
