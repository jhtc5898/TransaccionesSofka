package com.sofka.movimientos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientKafkaDTO {

    @JsonProperty("idClient")
    private UUID idClient;

    @JsonProperty("name")
    private String name;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("age")
    private Long age;

    @JsonProperty("identificationCard")
    private String identificationCard;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("status")
    private Boolean status;


}
