package com.example.redditclone.repository;

import com.example.redditclone.entity.Post;
import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
    Post findByPostid(Long id);
    List<Post> findBySubreddit(SubReddit subredditname);
    List<Post> findByUser(User user);
}
