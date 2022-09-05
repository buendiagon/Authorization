package com.uis.authorization.service.impl;

import com.uis.authorization.dto.UserDTO;
import com.uis.authorization.exception.DataNotFoundException;
import com.uis.authorization.exception.TransactionException;
import com.uis.authorization.mappers.UserMapper;
import com.uis.authorization.model.Friends;
import com.uis.authorization.model.User;
import com.uis.authorization.repository.IFriendsRepository;
import com.uis.authorization.repository.IUserRepository;
import com.uis.authorization.security.JwtTokenUtil;
import com.uis.authorization.service.interfaces.IUserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    private JwtTokenUtil jwtTokenUtil;

    private IFriendsRepository friendsRepository;

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

    @Override
    public List<UserDTO> getFriendsByToken(String token) {
        String jwtToken = jwtTokenUtil.getTokenWithoutBearer(token);
        Long userId = jwtTokenUtil.getClaimFromToken(jwtToken, (Claims claims) -> claims.get("idUser", Long.class));
        List<Friends> friends = this.friendsRepository.findAllByIdUser1OrIdUser2(userId)
                .orElse(new ArrayList<>());
        List<UserDTO> friendsDTO = new ArrayList<>();
        for (Friends friend : friends) {
            if (friend.getIdUser1().equals(userId)) {
                friendsDTO.add(UserMapper.INSTANCE.toUserDTO(this.userRepository.findById(friend.getIdUser2())
                        .orElseThrow((() -> new DataNotFoundException("User not found")))));
            } else {
                friendsDTO.add(UserMapper.INSTANCE.toUserDTO(this.userRepository.findById(friend.getIdUser1())
                        .orElseThrow((() -> new DataNotFoundException("User not found")))));
            }
        }
        return friendsDTO;
    }

    @Override
    public UserDTO addFriend(String token, String username) {
        String jwtToken = jwtTokenUtil.getTokenWithoutBearer(token);
        Long userId = jwtTokenUtil.getClaimFromToken(jwtToken, (Claims claims) -> claims.get("idUser", Long.class));
        User user = this.userRepository.findTopByUsername(username)
                .orElseThrow((() -> new DataNotFoundException("User not found here" + username)));
        Friends friend = this.friendsRepository.findByIdUser1AndIdUser2(userId, user.getId())
                .orElse(null);
        if (friend == null) {
            friend = new Friends();
            friend.setIdUser1(userId);
            friend.setIdUser2(user.getId());
            // De momento no se guarda el estado de la solicitud
            // simplemente se guarda como aceptada por defecto
            friend.setConfirmed(true);
            this.friendsRepository.save(friend);
        }
        return UserMapper.INSTANCE.toUserDTO(user);
    }

    @Override
    public Boolean addPhotoUserByToken(String token, String photo_url) {
        String usernamee = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                usernamee = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                System.out.println("JwtRequestFilter: " + e.getMessage());
            }
        } else {
            System.out.println("JwtRequestFilter: No token found in request header");
        }
        User user = this.userRepository.findTopByUsername(usernamee)
                .orElseThrow((() -> new DataNotFoundException("User not found")));
        user.setUserPhotoUrl(photo_url);
        this.userRepository.save(user);
        return true;
    }

    @Override
    public Boolean editNameLastname(String token, String names,String lastNames) {
        String usernamee = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            try {
                usernamee = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (Exception e) {
                System.out.println("JwtRequestFilter: " + e.getMessage());
            }
        } else {
            System.out.println("JwtRequestFilter: No token found in request header");
        }
        User user = this.userRepository.findTopByUsername(usernamee)
                .orElseThrow((() -> new DataNotFoundException("User not found")));
        user.setNames(names);
        user.setLastNames(lastNames);
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

    @Autowired
    public void setFriendsRepository(IFriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }

}
