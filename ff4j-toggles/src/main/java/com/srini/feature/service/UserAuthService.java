package com.srini.feature.service;

import java.util.ArrayList;
import java.util.List;

import com.srini.feature.dao.UserDAO;
import com.srini.feature.model.MyUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    private UserDAO userDao;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        List<MyUserDetails> myUserDetails = userDao.getUser(username);
        if (myUserDetails == null) {// should have proper handling of Exception
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (myUserDetails.isEmpty()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("anything");
            grantedAuthorities.add(grantedAuthority);
            return new User("srini", "pass", grantedAuthorities);
        }

        String userName = "";
        String password = "";
        for (MyUserDetails userDetail : myUserDetails) {
            userName = userDetail.getUsername();
            password = userDetail.getPassword();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userDetail.getRole());
            grantedAuthorities.add(grantedAuthority);
        }
        UserDetails userDetails = new User(userName, password, grantedAuthorities);

        return userDetails;
    }
}
