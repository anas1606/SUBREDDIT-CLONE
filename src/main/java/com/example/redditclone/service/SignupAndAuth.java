package com.example.redditclone.service;

import com.example.redditclone.dao.UserDetaildao;
import com.example.redditclone.dao.Varificationtokendao;
import com.example.redditclone.dto.Logindto;
import com.example.redditclone.dto.UserConverter;
import com.example.redditclone.dto.UserDto;
import com.example.redditclone.entity.User;
import com.example.redditclone.jwt.JWTProvider;
import com.example.redditclone.repository.TokenDetail;
import com.example.redditclone.repository.UserDetail;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;


@Service
@Slf4j
public class SignupAndAuth {
    @Autowired
    @Qualifier("userdetail")
    private UserDetail userdetail;
    @Autowired
    @Qualifier("tokendetail")
    private TokenDetail tokendetail;
    @Autowired
    @Qualifier("userdetaildao")
    private UserDetaildao userdetaildao;
    @Autowired
    @Qualifier("verificationtokendao")
    private Varificationtokendao verificationtokendao;
    @Autowired
    private JWTProvider jwtprovider;
    @Autowired
    private UserConverter converter;

    @Transactional
    public UserDto signup(UserDto userdto){
        User data = converter.dtotouser(userdto);
        userdetaildao.signup(data);
        log.info("User Data inserted");
        verificationtokendao.getvarificationtoken(data);
        log.info("Token Is send to user");

        return converter.usertodto(data);
    }

    @Transactional
    public ResponseEntity<String> verifytoken(String token){
        log.info("Request For Token Varification");
            var tokendata = tokendetail.findByToken(token);
        if(tokendata !=null) {
            var ispresent = userdetail.findByUsername(tokendata.getUser().getUsername());
            if (ispresent != null) {
                ispresent.setEnabled(true);
                userdetail.save(ispresent);
                return new ResponseEntity<>("Varifyed Successfully...!!!!",HttpStatus.OK);
            }
        }
        return status(HttpStatus.BAD_REQUEST).body("Invalid Token");
    }

    @SneakyThrows
    public ResponseEntity<Map<String,String>> login(Logindto dto){
            var result = new HashMap<String, String>();
            var ispresent = userdetail.findByUsername(dto.getUsername());

            if(ispresent!=null)
            {
                log.info("User Is present");
                if(ispresent.isEnabled())
                {
                    result.put("token",jwtprovider.generateToken(dto.getUsername()));
                    result.put("Username",dto.getUsername());
                    return status(HttpStatus.ACCEPTED).body(result);
                }
                else {
                    log.info("But Not Varifyed");
                    result.put("Error","Varify the user First");
                    result.put("UserName",dto.getUsername());
                    return status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
                }
            }

            log.info("Not A Valid User");
        result.put("Error","Bad Credential (User Not Found)");
        result.put("User",dto.getUsername());
        return status(HttpStatus.BAD_REQUEST).body(result);
    }
}
