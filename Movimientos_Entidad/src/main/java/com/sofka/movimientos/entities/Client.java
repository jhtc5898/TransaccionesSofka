package com.sofka.movimientos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "mov_tclient")
@JsonIgnoreProperties({"password"})
public class Client extends Person implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idClient")
    private UUID idClient;


    @Comment("Client password")
    @Column(name = "password", nullable = false)
    private String password;

    @Comment("Status Client ")
    @Column(name = "status", nullable = false)
    private Boolean status;


    public Client() {

    }
}