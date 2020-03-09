package com.ruscassie.litige.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the address database table.
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4286430782875115212L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "NUMERIC(19,0)")
    private Long id;

    private String country;

    private String detail;

    @Column(name = "post_office_box")
    private String postOfficeBox;

    private String town;

    // bi-directional many-to-one association to User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
