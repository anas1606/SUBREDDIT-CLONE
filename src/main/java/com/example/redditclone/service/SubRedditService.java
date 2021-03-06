package com.example.redditclone.service;

import com.example.redditclone.dto.SubRedditConverter;
import com.example.redditclone.dto.SubRedditdto;
import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.SubRedditRepo;
import com.example.redditclone.repository.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubRedditService {
    @Autowired
    @Qualifier("subredditrepo")
    private  SubRedditRepo subredditrepository;
    @Autowired
    private  SubRedditConverter subredditmapper;
    @Autowired
    private UserDetail userdetail;

    @Transactional
    public SubRedditdto save(SubRedditdto subredditDto) {
        SubReddit save = subredditrepository.save(subredditmapper.mapdtotosubreddit(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional
    public List<SubRedditdto> getallsubreddit(){
        log.info("get the All subreddit");
        return subredditrepository.findAll()
                .stream()
                .map(subredditmapper::mapsubreddittodto)
                .collect(Collectors.toList());
    }

    public List<SubRedditdto> getbyuser() {
        log.info("get the All subreddit by username");
        return subredditmapper.listmapsubreddittodto(
                subredditrepository.findByUser(
                        userdetail.findByUsername(new JWTProvider().getcurrentuser())
                )
            );
    }
}
