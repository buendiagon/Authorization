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
@Table(schema = "campuslink", name = "access_control")
public class AccessControl implements Serializable {
    private static final long serialVersionUID = -4358981442375745702L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "last_use", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lastAccess;
}
