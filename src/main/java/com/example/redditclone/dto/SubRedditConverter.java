package com.example.redditclone.dto;

import com.example.redditclone.entity.SubReddit;
import com.example.redditclone.entity.User;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.UserDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SubRedditConverter {

    private JWTProvider jwtprovider;
    private  UserDetail userdetail;

    public SubReddit mapdtotosubreddit(SubRedditdto dto){
        String username = jwtprovider.getcurrentuser();
        User user = userdetail.findByUsername(username);

        SubReddit reddit = new SubReddit();
        reddit.setId(dto.getId());
        reddit.setName(dto.getName());
        reddit.setDescription(dto.getDescription());
        reddit.setCreatedDate(Instant.now());
        reddit.setUser(user);
        return reddit;
    }

    public SubRedditdto mapsubreddittodto(SubReddit reddit){
        SubRedditdto dto = new SubRedditdto();
        dto.setId(reddit.getId());
        dto.setName(reddit.getName());
        dto.setDescription(reddit.getDescription());
        dto.setNumberOfPosts(reddit.getPosts().size());
        return dto;
    }

    public List<SubRedditdto> listmapsubreddittodto(List<SubReddit> reddit) {
        return reddit.stream()
                .map(this::mapsubreddittodto)
                .collect(Collectors.toList());
    }
}
