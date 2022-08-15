package com.uis.authorization.service.impl;

import com.uis.authorization.dto.UserDTO;
import com.uis.authorization.mappers.UserMapper;
import com.uis.authorization.model.User;
import com.uis.authorization.repository.IUserRepository;
import com.uis.authorization.security.JwtTokenUtil;
import com.uis.authorization.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDTO getUserDataByToken(String token) {
        String username = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                System.out.println("JwtRequestFilter: " + e.getMessage());
            }
        } else {
            System.out.println("JwtRequestFilter: No token found in request header");
        }
        User user = this.userRepository.findTopByUsername(username)
                .orElseThrow((() -> new RuntimeException("User not found")));
        return UserMapper.INSTANCE.toUserDTO(user);
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

}
