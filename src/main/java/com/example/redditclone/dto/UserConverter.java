package com.example.redditclone.dto;

import com.example.redditclone.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserConverter {
    public UserDto usertodto(User user){
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        return dto;
    }
    public User dtotouser(UserDto dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }

    public List<UserDto> usertodtolist(List<User> user){
        return user.stream()
                .map(this::usertodto)
                .collect(Collectors.toList());
    }
}
