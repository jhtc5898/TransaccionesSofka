package com.sofka.movimientos.services;


import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;

public interface AccountService {
    ResponseData createAccount(MovementsDTO.createAccount movementsDTO);

    ResponseData listAccount();

    ResponseData editAccount(MovementsDTO.editAccount movementsDTO);

    ResponseData deleteAccount(String accountnumber);



}
