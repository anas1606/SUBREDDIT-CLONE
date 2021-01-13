package com.example.redditclone.service;

import com.example.redditclone.Exception.PostNotFoundException;
import com.example.redditclone.dto.Commentdto;
import com.example.redditclone.dto.Commentmapper;
import com.example.redditclone.entity.Comment;
import com.example.redditclone.entity.Post;
import com.example.redditclone.entity.User;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.CommentRepo;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.UserDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
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
        Post post = postrepo.findById(commentdto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentdto.getPostId().toString()));
        Comment comment = commentmapper.map(commentsDto, post, userdetail.findByUsername(new JWTProvider().getcurrentuser()));
        commentrepo.save(comment);
    }

    public List<Commentdto> getAllCommentsForPost(Long postId) {
        Post post = postrepo.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentrepo.findByPost(post)
                .stream()
                .map(commentmapper::mapToDto).collect(toList());
    }

    public List<Commentdto> getAllCommentsForUser(String userName) {
        User user = userdetail.findByusername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentrepo.findAllByUser(user)
                .stream()
                .map(commentmapper::mapToDto)
                .collect(toList());
    }
}
