package com.example.redditclone.service;

import com.example.redditclone.dto.PostMapper;
import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.entity.Post;
import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.SubRedditRepo;
import com.example.redditclone.repository.UserDetail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.ResponseEntity.status;

@Service
@Slf4j
@AllArgsConstructor
public class PostService {

    private PostRepo postrepo;
    private PostMapper postmapper;
    private SubRedditRepo subredditrepo;
    private UserDetail userdetail;

    @Transactional
    public PostRequest save(PostRequest postRequest) {
        try {
            Post save = postrepo.save(postmapper.map(postRequest));
            postRequest.setPostid(save.getPostid());

            //add post to subreddit post list
            subredditrepo.findByName(
                    postRequest.getSubredditname())
                    .getPosts()
                    .add(postmapper.map(postRequest)
                    );

            return postRequest;
        }catch (Exception e){
            log.error("Cant Save the Post");
            return null;
        }
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

    @Transactional
    public ResponseEntity<PostResponse> getallbyid(Long id) {
        try{
            return status(HttpStatus.OK).body(postmapper.maptodto(postrepo.findByPostid(id)));
        }
        catch (Exception e){
            log.error("getallbyid No Post Found");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @Transactional
    public ResponseEntity<List<PostResponse>> getbysubreddit(String subredditname) {
        try{
            SubReddit ispresent = subredditrepo.findByName(subredditname);
            if(ispresent == null){
                log.info("invalid Subreddit Name");
                return status(HttpStatus.BAD_REQUEST).body(null);
            }
            return status(HttpStatus.OK).body(
                    postrepo.findBySubreddit(ispresent)
                    .stream()
                    .map(postmapper::maptodto)
                    .collect(Collectors.toList())
            );
        }
        catch (Exception e){
            log.error("getbysubreddit no post error");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Transactional
    public ResponseEntity<List<PostResponse>> getbyuser() {
        try {
            return status(HttpStatus.OK).body(
                    postrepo.findByUser(
                            userdetail.findByUsername(new JWTProvider().getcurrentuser())
                    ).stream()
                    .map(postmapper::maptodto)
                    .collect(Collectors.toList())
            );
        }catch (Exception e){
            log.error("getbyuser no post error");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
