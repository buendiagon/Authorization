package com.uis.authorization.controller;

import com.uis.authorization.dto.SignInDTO;
import com.uis.authorization.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api")
public class AuthorizationController {

    private IUserRepository userRepository;

    @PostMapping("/signIn")
    public ResponseEntity<Boolean> signIn(@Valid @RequestBody SignInDTO signInDTO) {
        return new ResponseEntity<>(this.userRepository.findTopByusernameAndPassword(signInDTO.getUsername(), signInDTO.getPassword()) != null, HttpStatus.OK);
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
