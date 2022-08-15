package com.uis.authorization.controller;

import com.uis.authorization.dto.JwtDTO;
import com.uis.authorization.dto.LoginDTO;
import com.uis.authorization.dto.UserDTO;
import com.uis.authorization.service.interfaces.IAuthorizationService;
import com.uis.authorization.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Daniel Adrian Gonzalez Buendia
 **/
@RestController
@RequestMapping("/public/api")
public class AuthorizationController {

    private IAuthorizationService authorizationService;
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(this.authorizationService.login(loginDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<Boolean> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(this.userService.createUser(userDTO));
    }

    @Autowired
    public void setAuthorizationService(IAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

}
