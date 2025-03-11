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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class AccountImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final AccountMapper accountMapper;
    private final MovementsRepository movementsRepository;

    @Override
    @Transactional
    public ResponseData createAccount(MovementsDTO.createAccount movementsDTO) {
        log.debug("Creating account: {}", movementsDTO);
        ResponseData response = new ResponseData();

        Client client = clientRepository.findByIdentificationCard(movementsDTO.getIdentificationCard());
        if (client == null) {
            return response.withMessage("Client not found");
        }

        Account account = createAndSaveAccount(movementsDTO, client);
        createInitialMovement(account);

        return response.withMessage("Account created successfully").withData(Map.of(SAVE_ACCOUNT, account));
    }

    private Account createAndSaveAccount(MovementsDTO.createAccount movementsDTO, Client client) {
        Account account = accountMapper.toEntity(movementsDTO);
        account.setAccountnumber(generarNumeroCuenta());
        account.setClient(client);
        return accountRepository.save(account);
    }

    private void createInitialMovement(Account account) {
        Movements movements = new Movements();
        movements.setMovementdate(new Date());
        movements.setMovementtype(account.getAccounttype());
        movements.setMovementvalue(account.getInitialbalance());
        movements.setBalance(account.getInitialbalance());
        movements.setStatus(true);
        movements.setAccount(account);
        movementsRepository.save(movements);
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
        log.debug("Editing account: {}", movementsDTO);
        ResponseData response = new ResponseData();

        Account account = accountRepository.findByAccountnumber(movementsDTO.getAccountnumberOld());
        if (account == null) {
            return response.withMessage("Account not found");
        }

        if (!account.getStatus()) {
            return response.withMessage("Account is disabled");
        }

        account.setAccountnumber(movementsDTO.getAccountnumberNew());
        accountRepository.save(account);

        return response.withMessage("Account updated successfully").withData(Map.of(EDIT_ACCOUNT, account));
    }

    @Override
    @Transactional
    public ResponseData deleteAccount(String accountnumber) {
        log.debug("Deleting account: {}", accountnumber);
        ResponseData response = new ResponseData();

        Account account = accountRepository.findByAccountnumber(accountnumber);
        if (account == null) {
            return response.withMessage("Account not found");
        }

        if (!account.getStatus()) {
            return response.withMessage("Account is already disabled");
        }

        account.setStatus(false);
        return response.withMessage("Account deleted successfully").withData(Map.of(DELETE_ACCOUNT, true));
    }
}
