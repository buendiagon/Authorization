package com.uis.authorization.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Daniel Adrian Gonzalez Buendia
 */

@Entity
@Data
@NoArgsConstructor
@Table(schema = "public", name = "ACCESS_CONTROL")
public class AccessControl implements Serializable {
    private static final long serialVersionUID = -4358981442375745702L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ID_USER", nullable = false)
    private Long idUser;

    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "LAST_USE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lastAccess;
}
