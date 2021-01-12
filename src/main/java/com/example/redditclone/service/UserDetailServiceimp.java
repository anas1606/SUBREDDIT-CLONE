package com.example.redditclone.service;

import com.example.redditclone.entity.User;
import com.example.redditclone.repository.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Service
public class UserDetailServiceimp implements UserDetailsService {
    @Autowired
    @Qualifier("userdetail")
    private UserDetail userdetail;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException, DataAccessException {
        Optional<User> userOptional = Optional.ofNullable(userdetail.findByUsername(s));
        User user = userOptional
                .orElseThrow(()->new UsernameNotFoundException("No User"+" found with username: "+s));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        var userrole = new org.springframework.security.core.authority.SimpleGrantedAuthority(role);
        return singletonList(userrole);
    }
}
