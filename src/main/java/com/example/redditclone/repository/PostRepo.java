package com.example.redditclone.repository;

import com.example.redditclone.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    Post findByPostid(Long id);
}
