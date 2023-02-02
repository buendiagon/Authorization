package com.uis.authorization.service.impl;

import com.uis.authorization.dto.UserDTO;
import com.uis.authorization.exception.DataNotFoundException;
import com.uis.authorization.exception.TransactionException;
import com.uis.authorization.mappers.UserMapper;
import com.uis.authorization.model.User;
import com.uis.authorization.repository.IUserRepository;
import com.uis.authorization.security.JwtTokenUtil;
import com.uis.authorization.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
                .orElseThrow((() -> new DataNotFoundException("User not found")));
        return UserMapper.INSTANCE.toUserDTO(user);
    }

    @Override
    public Boolean createUser(UserDTO userDTO) {
        // check if user already exists
        if (this.userRepository.findTopByUsername(userDTO.getUsername()).isPresent()) {
            throw new TransactionException("User already exists");
        }
        User user = UserMapper.INSTANCE.toUser(userDTO);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        this.userRepository.save(user);
        return true;
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
