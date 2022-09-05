package com.uis.authorization.controller;

import com.uis.authorization.dto.UserDTO;
import com.uis.authorization.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/profile")
    public ResponseEntity<Boolean> addPhotoUser(@RequestHeader("Authorization") String token,@RequestParam String photo_url){
        return ResponseEntity.ok(this.userService.addPhotoUserByToken(token,photo_url));
    }
    @PutMapping("/profileDetails")
    public ResponseEntity<Boolean> editNameLastname(@RequestParam Long idUser,@RequestParam String names,@RequestParam String lastLames){
        return ResponseEntity.ok(this.userService.editNameLastname(idUser,names,lastLames));
    }

    @GetMapping("/friends")
    public ResponseEntity<List<UserDTO>> getFriends(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(this.userService.getFriendsByToken(token));
    }

    @PostMapping("/friend")
    public ResponseEntity<UserDTO> addFriend(@RequestHeader("Authorization") String token, @RequestParam String username) {
        return ResponseEntity.ok(this.userService.addFriend(token, username));
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
