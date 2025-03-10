package com.sofka.movimientos.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MovementsDTO {
    @Data
    public static class createAccount {

        @NotNull
        private String identificationCard;

        @NotNull
        @Pattern(regexp = "^[AC]$", message = "El tipo de cuenta debe ser 'A' o 'C'")
        private String accounttype;

        @NotNull
        private BigDecimal initialbalance;

    }

    @Data
    public static class editAccount{
        @NotNull
        private String accountnumberOld;

        @NotNull
        private String accountnumberNew;

    }

    @Data
    public static class accountIdent{
        @NotNull
        private String accountnumber;

    }


    @Data
    public static class createMovement {

        @NotNull
        private String movementtype;

        @NotNull
        private BigDecimal movementvalue;

        @NotNull
        private String accountnumber;

    }

    @Data
    public static class editMovement{
        @NotNull
        private String movementtype;

    }

    @Data
    public static class MovementIdent{
        @NotNull
        private String idmovement;
    }


    @Data
    public static class reportMovement {

        @NotNull
        private String identificationCard;

        @NotNull
        private String dateInit;

        @NotNull
        private String dateEnd;

    }

    @Data
    public static class reportMovementResponse {

        private Date movementdate;

        private String name;

        private String accountnumber;

        private String movementtype;

        private BigDecimal initialbalance;

        private Boolean status;

        private BigDecimal movementvalue;

        private BigDecimal balance;

    }

}
