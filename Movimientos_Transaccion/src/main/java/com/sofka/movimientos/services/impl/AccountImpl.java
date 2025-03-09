package com.sofka.movimientos.services.impl;

import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.entities.Account;
import com.sofka.movimientos.entities.Client;
import com.sofka.movimientos.entities.Movements;
import com.sofka.movimientos.mapper.AccountMapper;
import com.sofka.movimientos.repositories.AccountRepository;
import com.sofka.movimientos.repositories.ClientRepository;
import com.sofka.movimientos.repositories.MovementsRepository;
import com.sofka.movimientos.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sofka.movimientos.Utils.AccountNumberGenerator.generarNumeroCuenta;

import static com.sofka.movimientos.Utils.Constants.*;


@Slf4j
@Service
public class AccountImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MovementsRepository movementsRepository;

    @Override
    @Transactional
    public ResponseData createAccount(MovementsDTO.createAccount movementsDTO){
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("Service to save a movements : {}", movementsDTO);

        Client client = clientRepository.findByIdentificationCard(movementsDTO.getIdentificationCard());

        if (client == null) {
            response.setMessage("Client not found");
            return response;
        }

        Account account = accountMapper.toEntity(movementsDTO);
        account.setAccountnumber(generarNumeroCuenta());
        account.setClient(client);
        accountRepository.save(account);

        Movements movements = new Movements();
        Date date = new Date();
        movements.setMovementdate(date);
        movements.setMovementtype(account.getAccounttype());
        movements.setMovementvalue(account.getInitialbalance());
        movements.setBalance(account.getInitialbalance());
        movements.setStatus(Boolean.TRUE);
        movements.setAccount(account);
        movementsRepository.save(movements);

        data.put(SAVE_ACCOUNT, account);
        response.setMessage("Account created successfully");
        response.setData(data);
        return response;
    }



    @Override
    public ResponseData listAccount() {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("List Info Account");
        data.put(LIST_ACCOUNT, accountRepository.findAll());
        if (data.isEmpty()) {
            data.put(LIST_ACCOUNT, new ArrayList<>());
        }

        response.setData(data);
        response.setMessage("List of account");
        return response;
    }

    @Override
    @Transactional
    public ResponseData editAccount(MovementsDTO.editAccount movementsDTO) {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("Editing account : {}", movementsDTO);

        Account account = accountRepository.findByAccountnumber(movementsDTO.getAccountnumberOld());

        if (account == null) {
            response.setMessage("Account not found");
            return response;
        }

        if (!account.getStatus()) {
            response.setMessage("Account is disabled");
            return response;
        }

        account.setAccountnumber(movementsDTO.getAccountnumberNew());
        accountRepository.save(account);
        data.put(EDIT_ACCOUNT, account);
        response.setMessage("Account updated successfully");
        response.setData(data);
        return response;
    }





    @Override
    @Transactional
    public ResponseData deleteAccount(String accountnumber) {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("Delete Account : {}", accountnumber);

        Account account = accountRepository.findByAccountnumber(accountnumber);

        if (account == null) {
            response.setMessage("Account not found");
            return response;
        }

        if (account.getStatus()) {
            account.setStatus(Boolean.FALSE);
            data.put(DELETE_ACCOUNT, Boolean.TRUE);
            response.setMessage("Account deleted successfully");
            response.setData(data);

        } else {
            response.setMessage("Account is already disabled");
        }

        return response;
    }
}
