package com.example.redditclone.service;

import com.example.redditclone.exception.PostNotFoundException;
import com.example.redditclone.dto.Commentdto;
import com.example.redditclone.dto.Commentmapper;
import com.example.redditclone.entity.Comment;
import com.example.redditclone.entity.Post;
import com.example.redditclone.entity.User;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.CommentRepo;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.status;

@Service
@Slf4j
public class CommentService {

    private  PostRepo postrepo;
    private  UserDetail userdetail;
    private  Commentmapper commentmapper;
    private  CommentRepo commentrepo;
    private  Commentdto commentdto;

    public CommentService(PostRepo postrepo, UserDetail userdetail, CommentRepo commentrepo) {
        this.postrepo = postrepo;
        this.userdetail = userdetail;
        this.commentrepo = commentrepo;
    }

    public void save(Commentdto commentsDto) {
        Post post = postrepo.findById(commentdto.getPostid())
                .orElseThrow(() -> new PostNotFoundException(commentdto.getPostid().toString()));
        Comment comment = commentmapper.map(commentsDto, post, userdetail.findByUsername(new JWTProvider().getcurrentuser()));
        commentrepo.save(comment);
    }

    public ResponseEntity<List<Commentdto>> getAllCommentsForPost(Long postId) {
        try {

            Post post = postrepo.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
            return status(HttpStatus.OK).body(
                    commentrepo.findByPost(post)
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
            User user = userdetail.findByUsername(username);
            return status(HttpStatus.OK).body(
                    commentrepo.findAllByUser(user)
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
