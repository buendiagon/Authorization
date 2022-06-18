package com.uis.authorization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(schema = "public", name = "USERS")
public class User implements Serializable {
    private static final long serialVersionUID = -1424432944414355371L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE", nullable = false)
    private String phone;

    @Column(name = "NAMES", nullable = false)
    private String names;

    @Column(name = "LAST_NAMES", nullable = false)
    private String lastNames;

    @Column(name = "CREATED")
    @Temporal(TemporalType.DATE)
    private Date created;

    @Column(name = "UPDATED")
    @Temporal(TemporalType.DATE)
    private Date updated;
}
