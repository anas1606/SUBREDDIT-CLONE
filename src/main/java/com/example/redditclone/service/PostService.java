package com.example.redditclone.service;

import com.example.redditclone.dto.PostMapper;
import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.entity.Post;
import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.SubRedditRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.ResponseEntity.status;

@Service
@Slf4j
public class PostService {

    @Autowired
    private PostRepo postrepo;
    @Autowired
    private PostMapper postmapper;
    @Autowired
    private SubRedditRepo subredditrepo;
    private SubReddit subreddit;

    @Transactional
    public PostRequest save(PostRequest postRequest) {
        Post save = postrepo.save(postmapper.map(postRequest));
        postRequest.setPostid(save.getPostid());
        //add post to subreddit post list
        subredditrepo.findByName(postRequest.getSubredditname()).getPosts().add(postmapper.map(postRequest));

        return postRequest;
    }

    @Transactional
    public ResponseEntity<List<PostResponse>> getallposts() {
        try{
            return status(HttpStatus.OK).body(postrepo.findAll()
                    .stream()
                    .map(postmapper::maptodto)
                    .collect(Collectors.toList()));
        }
        catch (Exception e){
            log.error("No Post Found");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
        }

    public ResponseEntity<PostResponse> getallbyid(Long id) {
        try{
            return status(HttpStatus.OK).body(postmapper.maptodto(postrepo.findByPostid(id)));
        }
        catch (Exception e){
            log.error("No Post Found");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
