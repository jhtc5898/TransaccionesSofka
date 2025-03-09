package com.sofka.movimientos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "mov_taccount")
public class Account {

    private static final long serialVersionUUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idaccount")
    private UUID idaccount;

    @Comment("Account  number")
    @Column(name = "accountnumber", nullable = false)
    private String accountnumber;

    @Comment("Account type ")
    @Column(name = "accounttype", nullable = false)
    private String accounttype;

    @Comment("Account initial balance ")
    @Column(name = "initialbalance", nullable = false)
    private BigDecimal initialbalance;

    @Comment("Status Client ")
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idClient", nullable = false)
    @JsonIgnore
    private Client client;


}
