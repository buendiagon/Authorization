package com.uis.authorization.service.impl;

import com.uis.authorization.model.User;
import com.uis.authorization.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private IUserRepository userRepository;

    public User user;

    @Override
    public UserDetails loadUserByUsername(String username) {
        this.user = this.userRepository.findTopByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
