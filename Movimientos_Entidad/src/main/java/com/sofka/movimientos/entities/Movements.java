package com.sofka.movimientos.entities;

import lombok.Data;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Data
@Table(name = "mov_tmovement")
public class Movements {

    private static final long serialVersionUUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmovement")
    private UUID idmovement;

    @Comment("Movement date")
    @Column(name = "movementdate", nullable = false)
    private Date movementdate;

    @Comment("Movement type ")
    @Column(name = "movementtype", nullable = false)
    private String movementtype;

    @Comment("Movement value ")
    @Column(name = "movementvalue", nullable = false)
    private BigDecimal movementvalue;

    @Comment("Movement balance ")
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Comment("Status Client ")
    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idaccount", referencedColumnName = "idaccount" , nullable = false)
    private Account account;


}
