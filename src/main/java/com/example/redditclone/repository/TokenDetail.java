package com.example.redditclone.repository;
import com.example.redditclone.entity.User;
import com.example.redditclone.entity.VarificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("tokendetail")
public interface TokenDetail extends JpaRepository<VarificationToken,Long> {
    VarificationToken findByUser (User user);
    VarificationToken findByToken(String token);
}
