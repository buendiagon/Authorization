package com.uis.authorization.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Daniel Adrian Gonzalez Buendia
 **/
@Entity
@Data
@NoArgsConstructor
@Table(schema = "campuslink", name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -1424432944414355371L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "names", nullable = false)
    private String names;

    @Column(name = "lastnames", nullable = false)
    private String lastNames;

    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created = new Date();

    @Column(name = "updated")
    @Temporal(TemporalType.DATE)
    private Date updated = new Date();

    @Column(name="photo_user")
    private String userPhotoUrl;
}
