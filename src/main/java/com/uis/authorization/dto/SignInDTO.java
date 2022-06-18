package com.uis.authorization.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Daniel Adrian Gonzalez Buendia
 **/
@Getter
@Setter
public class SignInDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3796908352572727437L;

    private String username;
    private String password;
}
