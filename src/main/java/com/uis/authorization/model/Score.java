package com.uis.authorization.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @autor Juan David Morantes Vergara
 */
@Entity
@Data
@NoArgsConstructor
@Table(schema = "campuslink", name = "score")
public class Score implements Serializable {

    private static final long serialVersionUID = 4473092424437306387L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "id_user", nullable = false)
    private Long id_user;
    @Column(name = "id_input", nullable = false)
    private Long id_input;
    @Column(name = "is_positive", nullable = false)
    private Boolean is_positive;
}
