package com.example.redditclone.repository;
import com.example.redditclone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userdetail")
public interface UserDetail extends JpaRepository<User,Long> {
    User findByUsername(String user);
}
