package com.sofka.movimientos.mapper;

import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toEntity(MovementsDTO.createAccount dto) {
        Account account = new Account();
        account.setAccounttype(dto.getAccounttype());
        account.setInitialbalance(dto.getInitialbalance());
        account.setStatus(Boolean.TRUE);
        return account;
    }
}
