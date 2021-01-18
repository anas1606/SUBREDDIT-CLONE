package com.example.redditclone.service;

import com.example.redditclone.dto.VoteMapper;
import com.example.redditclone.dto.Votedto;
import com.example.redditclone.entity.Post;
import com.example.redditclone.entity.User;
import com.example.redditclone.entity.Vote;
import com.example.redditclone.entity.VoteType;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.PostRepo;
import com.example.redditclone.repository.UserDetail;
import com.example.redditclone.repository.VoteRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
@Slf4j
public class VoteService {
    private VoteType votetype;
    private Vote vote;
    @Autowired
    private VoteRepo voterepo;
    @Autowired
    private PostRepo postrepo;
    @Autowired
    private UserDetail userdetail;
    @Autowired
    private VoteMapper votemapper;

    public String test() {
        VoteType a = votetype.UPVOTE;
        if(!votetype.UPVOTE.equals(a))
            return "up";
        return "dw";
    }

    @Transactional
    public ResponseEntity<Votedto> save(Votedto dto) {
         Post post = postrepo.findByPostid(dto.getPostid());
            if(post == null){
                log.info("Post Not present To vote",dto.getPostid());
                return status(HttpStatus.BAD_REQUEST).body(null);
            }

            User user = userdetail.findByUsername(new JWTProvider().getcurrentuser());
            Optional<Vote> votebyuserandpost = voterepo.findTopByUserAndPostOrderByVoteIdDesc(user,post);
            if( votebyuserandpost.isPresent() &&
                    votebyuserandpost.get().getVoteType().equals(dto.getVotetype()) )
            {
                    log.info("User Already voted same");
                    return status(HttpStatus.BAD_REQUEST).body(null);
            }

            if(VoteType.UPVOTE.equals(dto.getVotetype())){
               post.setVotecount(post.getVotecount()+1);
            }else{
                post.setVotecount(post.getVotecount()-1);
            }

            Vote save = voterepo.save(votemapper.map(dto));
            dto.setId(save.getVoteId());
            postrepo.save(post);
            return status(HttpStatus.OK).body(dto);

    }

}
