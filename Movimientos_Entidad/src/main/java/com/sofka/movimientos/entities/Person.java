package com.sofka.movimientos.entities;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@MappedSuperclass
public class Person implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idClient", nullable = false)
    private UUID idClient;

    @Comment("Person first name")
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Comment("Person gender")
    @Column(name = "gender", length = 1)
    private String gender;


    @Comment("Person age")
    @Column(name = "age", length = 5)
    private Long age;

    @Comment("Person identification document")
    @Column(name = "identification_card", nullable = false, unique = true, length = 10)
    private String identificationCard;

    @Comment("Person Direction")
    @Column(name = "direction", length = 200, nullable = false)
    private String direction;

    @Comment("Person Phone")
    @Column(name = "phone", length = 20)
    private String phone;

    @Tolerate
    public Person() {
        super();
    }

    public Person(String identificationCard, String name, String direction) {
        this.identificationCard = identificationCard;
        this.name = name;
        this.direction = direction;
    }
}