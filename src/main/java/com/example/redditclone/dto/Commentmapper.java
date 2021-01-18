package com.example.redditclone.dto;
import com.example.redditclone.entity.Comment;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.CommentRepo;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class Commentmapper {
    @Autowired
    CommentRepo commentrpo = null;
    @Autowired
    private PostRepo postrepo;
    @Autowired
    private UserDetail userdetail;

    public Comment map(Commentdto commentdto){
        Comment comment = new Comment();

        comment.setId(getid());
        comment.setPost(postrepo.findByPostid(commentdto.getPostid()));
        comment.setUser(userdetail.findByUsername(new JWTProvider().getcurrentuser()));
        comment.setText(commentdto.getText());
        comment.setCreateddate(Instant.now());

        return comment;
    }

    public Commentdto mapToDto (Comment comment){
        Commentdto dto = new Commentdto();

        dto.setId(comment.getId());
        dto.setCreateddate(comment.getCreateddate());
        dto.setPostid(comment.getPost().getPostid());
        dto.setText(comment.getText());
        dto.setUsername(comment.getUser().getUsername());

        return dto;
    }
    private Long getid(){
        System.out.println(commentrpo.count());
        return (commentrpo.count());
    }
}
