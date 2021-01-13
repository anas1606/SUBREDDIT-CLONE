package com.example.redditclone.service;

import com.example.redditclone.dto.PostMapper;
import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.SubRedditRepo;
import com.example.redditclone.repository.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.status;

@Service
@Slf4j
public class PostService {

    @Autowired
    private SubRedditRepo subredditrepo;
    @Autowired
    private PostRepo postrepo;
    private PostMapper postmapper;
    @Autowired
    private UserDetail userdetail;

    public PostRequest save(PostRequest postRequest) {
        SubReddit subreddit = subredditrepo.findByName(postRequest.getSubredditname());
        postrepo.save(postmapper.map(postRequest, subreddit,userdetail.findByUsername(new JWTProvider().getcurrentuser())));
        return postRequest;
    }

    @Transactional
    public ResponseEntity<List<PostResponse>> getallposts() {
        try{
            return status(HttpStatus.OK).body(postrepo.findAll()
                    .stream()
                    .map(postmapper::mapToDto)
                    .collect(Collectors.toList()));
        }
        catch (Exception e){
            log.error("No Post Found");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
        }
}
