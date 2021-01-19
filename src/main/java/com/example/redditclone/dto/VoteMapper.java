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
        Vote vote = new Vote();

        vote.setVoteId(dto.getId());
        vote.setPost(postrepo.findByPostid(dto.getPostid()));
        vote.setVoteType(dto.getVotetype());
        vote.setUser(userdetail.findByUsername(new JWTProvider().getcurrentuser()));

        return vote;
    }

    public Votedto maptodto (Vote vote){
        Votedto dto = new Votedto();

        dto.setId(vote.getVoteId());
        dto.setVotetype(vote.getVoteType());
        dto.setPostid(vote.getPost().getPostid());

        return dto;
    }
}
