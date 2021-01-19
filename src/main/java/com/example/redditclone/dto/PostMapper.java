package com.example.redditclone.dto;

import com.example.redditclone.entity.Post;
import com.example.redditclone.repository.VoteRepo;
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
    @Autowired
    VoteRepo voterepo;


    public PostResponse maptodto(Post post){

        PostResponse postr = new PostResponse();

        postr.setId(post.getPostid());
        postr.setSubredditName(post.getSubreddit().getName());
        postr.setCommentCount(commentcount(post));
        postr.setDuration(getDuration(post));
        postr.setVoteCount(post.getVotecount());
        postr.setUpVote(post.getUpvote());
        postr.setDownVote(post.getDownvote());
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
        post.setVotecount(0);
        post.setUpvote(0);
        post.setDownvote(0);
        post.setUser(userdetail.findByUsername(new JWTProvider().getcurrentuser()));
        post.setSubreddit(subredditrepo.findByName(postRequest.getSubredditname()));

        return post;
    }
    private int commentcount(Post post){
        return (commentrepo.findByPost(post)).size();
    }
    private String getDuration(Post post) {
        return TimeAgo.using(post.getCreateddate().toEpochMilli());
    }
}
