package com.uis.authorization.service.interfaces;

import com.uis.authorization.dto.JwtDTO;
import com.uis.authorization.dto.LoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAuthorizationService {
    JwtDTO login(LoginDTO loginDTO);
}
