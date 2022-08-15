package com.uis.authorization.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 3885893295924816388L;

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String names;
    private String lastNames;
}
