package com.example.redditclone.repository;

import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("subredditrepo")
public interface SubRedditRepo extends JpaRepository<SubReddit,Long> {
    List<SubReddit> findByUser (User user);
    SubReddit findByName(String name);
}
