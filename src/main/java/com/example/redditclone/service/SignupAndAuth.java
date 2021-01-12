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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Service
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
    public void signup(UserDto userdto){
        User data = converter.dtotouser(userdto);
        userdetaildao.signup(data);
        verificationtokendao.getvarificationtoken(data);
    }

    @Transactional
    public String verifytoken(String token){
        var tokendata = tokendetail.findByToken(token);
        if(tokendata !=null) {
            var ispresent = userdetail.findByUsername(tokendata.getUser().getUsername());
            if (ispresent != null) {
                ispresent.setEnabled(true);
                userdetail.save(ispresent);
                return "Vlidation Successfull..!!!";
            }
        }
        return "Not a Valid Token";
    }

    @SneakyThrows
    public Map<String,String> login(Logindto dto){
            var result = new HashMap<String, String>();
            var ispresent = userdetail.findByUsername(dto.getUsername());

            if(ispresent!=null)
            {
                if(ispresent.isEnabled())
                {
                    result.put("token",jwtprovider.generateToken(dto.getUsername()));
                    result.put("Username",dto.getUsername());
                    return result;
                }
                else {
                    result.put("Error","Varify the user First");
                    result.put("UserName",dto.getUsername());
                    return result;
                }
            }
        result.put("Error","Bad Credential (User Not Found)");
        result.put("User",dto.getUsername());
        return result;
    }
}
