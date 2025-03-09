package com.sofka.movimientos.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ClientDTO {
    @Data
    public static class createClient {
        @NotNull
        @Size(min = 10, max = 10)
        private String identificationCard;

        @NotNull
        private String name;

        @NotNull
        private String gender;

        @NotNull
        @Range(min = 0, max = 200L)
        private Long age;

        @NotNull
        private String direction;

        @NotNull
        private String phone;

        @NotNull
        private String password;
    }

    @Data
    public static class editClient {
        @NotNull
        private String identificationCard;
        @NotNull
        private String passwordOld;
        @NotNull
        private String passwordNew;

    }

    @Data
    public static class clientIdent {
        @NotNull
        private String identificationCard;

    }


}
