package com.example.redditclone.repository;

import com.example.redditclone.entity.Comment;
import com.example.redditclone.entity.Post;
import com.example.redditclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findAllByUser (User user);
    List<Comment> findByPost (Post post);
}
