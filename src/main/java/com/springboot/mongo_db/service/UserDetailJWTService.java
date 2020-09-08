package com.springboot.mongo_db.service;


import com.springboot.mongo_db.model.UserAdmin;
import com.springboot.mongo_db.repository.IUserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailJWTService implements UserDetailsService {

    @Autowired
    IUserAdminRepository repo;

    @Override
    public UserDetails loadUserByUsername(String userAdmin) throws UsernameNotFoundException {

        UserAdmin us = repo.findByUser(userAdmin);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ADMIN"));

        //UserDetails userDetail = new User(us.getUser(), us.getPassword(), roles);
        UserDetails userDetail = new User("admin","1234", roles);
        return userDetail;
    }
}
