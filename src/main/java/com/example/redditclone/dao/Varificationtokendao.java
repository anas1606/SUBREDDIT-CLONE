package com.example.redditclone.dao;
import com.example.redditclone.entity.User;
import com.example.redditclone.entity.VarificationToken;
import com.example.redditclone.repository.TokenDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.UUID;

@Repository("verificationtokendao")
@Slf4j
public class Varificationtokendao {

    @Autowired
    @Qualifier("tokendetail")
    private TokenDetail tokendetail;

    public void getvarificationtoken(User user){
        VarificationToken token = new VarificationToken();
        var result= tokendetail.findByUser(user);
        if(result != null) {
            log.info("User Exist Updating the token");
            token.setId(result.getId());
        }

        token.setToken(gettoken());
        token.setUser(user);
        token.setExpdate(Instant.now());
        tokendetail.save(token);
    }

    private String gettoken(){
        return UUID.randomUUID().toString();
    }
}
