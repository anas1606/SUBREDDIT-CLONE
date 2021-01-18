package com.example.redditclone.dto;

import com.example.redditclone.entity.Vote;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VoteMapper {
    private Vote vote;
    @Autowired
    private PostRepo postrepo;
    @Autowired
    private UserDetail userdetail;

    public Vote map(Votedto dto){
        System.out.println("aa");
        Vote vote = new Vote();

        vote.setVoteId(1L);
        vote.setPost(postrepo.findByPostid(dto.getPostid()));
        vote.setVoteType(dto.getVotetype());
        vote.setUser(userdetail.findByUsername(new JWTProvider().getcurrentuser()));

        return vote;
    }
}
