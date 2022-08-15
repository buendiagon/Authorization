package com.uis.authorization.service.interfaces;

import com.uis.authorization.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Daniel Adrian Gonzalez Buendia
 */

@Service
public interface IUserService {
    UserDTO getUserDataByToken(String token);

    Boolean createUser(UserDTO userDTO);

    List<UserDTO> getFriendsByToken(String token);

    UserDTO addFriend(String token, String username);
}
