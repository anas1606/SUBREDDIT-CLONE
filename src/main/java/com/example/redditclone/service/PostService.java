package com.example.redditclone.service;

import com.example.redditclone.dto.PostMapper;
import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.SubRedditRepo;
import com.example.redditclone.repository.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private SubRedditRepo subredditrepo;
    @Autowired
    private PostRepo postrepo;
    private PostMapper postmapper;
    @Autowired
    private UserDetail userdetail;

    public void save(PostRequest postRequest) {
        SubReddit subreddit = subredditrepo.findByName(postRequest.getSubredditName());
        postrepo.save(postmapper.map(postRequest, subreddit,userdetail.findByUsername(new JWTProvider().getcurrentuser())));
    }

    @Transactional
    public List<PostResponse> getallposts() {
            return postrepo.findAll()
                    .stream()
                    .map(postmapper::mapToDto)
                    .collect(Collectors.toList());
        }
}
