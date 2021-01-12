package com.example.redditclone.dao;

import com.example.redditclone.entity.User;
import com.example.redditclone.repository.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.time.Instant;

@Repository("userdetaildao")
public class UserDetaildao {

    @Autowired
    @Qualifier("userdetail")
    private UserDetail userdetail;
    @Autowired
    private PasswordEncoder passwordencoder;

    public void signup(User user){
        User userdata = new User();
        var result = userdetail.findByUsername(user.getUsername());
        if(result != null)
            userdata.setUsername(result.getUsername());

        userdata.setUsername(user.getUsername());
        userdata.setPassword(passwordencoder.encode(user.getPassword()));
        userdata.setEmail(user.getEmail());
        userdata.setCreated(Instant.now());
        userdata.setEnabled(false);

        userdetail.save(userdata);
    }
}
