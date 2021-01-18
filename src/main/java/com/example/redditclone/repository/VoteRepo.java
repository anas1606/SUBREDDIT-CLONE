package com.example.redditclone.repository;

import com.example.redditclone.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepo extends JpaRepository<Vote,Long> {
}
