package com.uis.authorization.controller;

import com.uis.authorization.dto.UserDTO;
import com.uis.authorization.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Daniel Adrian Gonzalez Buendia
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private IUserService userService;


    @GetMapping
    public ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String token) {
        UserDTO userDTO = this.userService.getUserDataByToken(token);
        return ResponseEntity.ok(userDTO);
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
