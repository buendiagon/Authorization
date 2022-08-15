package com.uis.authorization.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Daniel Adrian Gonzalez Buendia
 */

@Entity
@Data
@NoArgsConstructor
@Table(schema = "public", name = "FRIENDS")
public class Friends implements Serializable {
    private static final long serialVersionUID = 2975397571611808026L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER1", insertable = false, updatable = false)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER2", insertable = false, updatable = false)
    private User user2;

    @Column(name = "ID_USER1", nullable = false)
    private Long idUser1;

    @Column(name = "ID_USER2", nullable = false)
    private Long idUser2;

    @Column(name = "CONFIRMED", nullable = false)
    private Boolean confirmed;
}
