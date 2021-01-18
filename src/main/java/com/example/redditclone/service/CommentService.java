package com.example.redditclone.service;

import com.example.redditclone.entity.Post;
import com.example.redditclone.exception.PostNotFoundException;
import com.example.redditclone.dto.Commentdto;
import com.example.redditclone.dto.Commentmapper;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.CommentRepo;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.UserDetail;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.status;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private  Commentmapper commentmapper;
    @Autowired
    private  CommentRepo commentrepo;
    private  Commentdto commentdto;
    @Autowired
    private PostRepo postrepo;
    @Autowired
    private UserDetail userdetail;

    public void save(Commentdto commentsDto) {
        commentrepo.save(commentmapper.map(commentsDto));
    }

    public ResponseEntity<List<Commentdto>> getAllCommentsForPost(Long postId) {
        try {
            return status(HttpStatus.OK).body(
                    commentrepo.findByPost(postrepo.findByPostid(postId))
                    .stream()
                    .map(commentmapper::mapToDto).collect(toList())
            );
        }
        catch(Exception e){
            log.error("Error Will getting All CommentByPostId");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseEntity<List<Commentdto>> getAllCommentsForUser(String username) {
        try {
            return status(HttpStatus.OK).body(
                    commentrepo.findAllByUser(userdetail.findByUsername(username))
                    .stream()
                    .map(commentmapper::mapToDto)
                    .collect(toList())
            );
        }
        catch(Exception ex){
            log.error("Error Will getting All CommentByUsername");
            return status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
