package com.sofka.movimientos.services.impl;

import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;

import com.sofka.movimientos.entities.Account;
import com.sofka.movimientos.entities.Client;
import com.sofka.movimientos.entities.Movements;

import com.sofka.movimientos.mapper.MovementMapper;

import com.sofka.movimientos.repositories.AccountRepository;
import com.sofka.movimientos.repositories.ClientRepository;
import com.sofka.movimientos.repositories.MovementsRepository;
import com.sofka.movimientos.services.MovementsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.sofka.movimientos.Utils.Constants.*;
import static com.sofka.movimientos.Utils.Util.getEndOfDay;
import static com.sofka.movimientos.Utils.Util.getStartOfDay;


@Slf4j
@Service
public class MovementsImpl implements MovementsService{

    @Autowired
    private MovementsRepository movementsRepository;


    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public ResponseData createMovement(MovementsDTO.createMovement movementsDTO){
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("Service to save a movements : {}", movementsDTO);

        Account account = accountRepository.findByAccountnumber(movementsDTO.getAccountnumber());

        if (account == null) {
            response.setMessage("Account not found");
            return response;
        }
        if(!account.getStatus()){
            response.setMessage("Account is inactive");
            return response;
        }

        //Ultimo movimiento realizado
        Movements movements = movementsRepository.findTopByMovementtypeAndAccount_IdaccountOrderByMovementdateDesc(movementsDTO.getMovementtype(), account.getIdaccount());

        if (movements == null) {
            response.setMessage("No movements found");
            return response;
        }
        if (!movements.getStatus()) {
            response.setMessage("The account is inactive");
            return response;
        }

        Movements movementsNew = new Movements();
        Date date = new Date();
        movementsNew.setMovementdate(date);
        movementsNew.setMovementtype(movementsDTO.getMovementtype());
        movementsNew.setMovementvalue(movementsDTO.getMovementvalue());
        BigDecimal calculateBalance = calculateBalance(movements.getBalance(), movementsNew.getMovementvalue());
        if(calculateBalance.compareTo(BigDecimal.ZERO) < 0){
            response.setMessage("The balance is insufficient");
            return response;
        }
        movementsNew.setBalance(calculateBalance);
        movementsNew.setStatus(Boolean.TRUE);
        movementsNew.setAccount(account);
        movementsRepository.save(movementsNew);

        data.put(SAVE_MOVEMENTS, movementsNew);
        response.setMessage("Movements created successfully");
        response.setData(data);
        return response;
    }

    private BigDecimal calculateBalance (BigDecimal movementsOld, BigDecimal movementsNew){
        return movementsOld.add(movementsNew);
    }



    @Override
    public ResponseData listMovement() {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("List Info Movement");
        data.put(LIST_MOVEMENTS, movementsRepository.findAll());
        if (data.isEmpty()) {
            data.put(LIST_MOVEMENTS, new ArrayList<>());
        }

        response.setData(data);
        response.setMessage("List of movements");
        return response;
    }

    @Override
    public ResponseData reportMovement(MovementsDTO.reportMovement reportMovement)
    {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();

        log.debug("List Report Movement");

        Client client = clientRepository.findByIdentificationCard(reportMovement.getIdentificationCard());

        if(client == null){
            response.setMessage("Client not found");
            return response;
        }

        Map<String, Map<String, List<MovementsDTO.reportMovementResponse>>> mapClient = new HashMap<>();
        Map<String, List<MovementsDTO.reportMovementResponse>> mapMovements = new HashMap<>();


        List<Account> account = accountRepository.findByClient_IdClient(client.getIdClient());


        List<MovementsDTO.reportMovementResponse> reportMovementResponses = new ArrayList<>();


        account.forEach(account1 -> {
            List<Movements> movements =  getMovementsForAccount(account1, reportMovement);

            movements.forEach(movements1 -> {
                MovementsDTO.reportMovementResponse reportMovementResponse = new MovementsDTO.reportMovementResponse();
                reportMovementResponse.setMovementdate(movements1.getMovementdate());
                reportMovementResponse.setName(client.getName());
                reportMovementResponse.setAccountnumber(account1.getAccountnumber());
                reportMovementResponse.setMovementtype(movements1.getMovementtype());
                reportMovementResponse.setInitialbalance(account1.getInitialbalance());
                reportMovementResponse.setStatus(movements1.getStatus());
                reportMovementResponse.setMovementvalue(movements1.getMovementvalue());
                reportMovementResponse.setBalance(movements1.getBalance());
                reportMovementResponses.add(reportMovementResponse);
            });

            mapMovements.put(account1.getAccountnumber(), reportMovementResponses);
        });


        mapClient.put(client.getName()+"-"+client.getIdentificationCard(), mapMovements);
        data.put("report", reportMovementResponses);
        response.setData(data);
        response.setMessage("Report of movements");
        return response;

    }

    @Override
    public ResponseData reportMovementFormat(MovementsDTO.reportMovement reportMovement)
    {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();

        log.debug("List Report Movement");

        Client client = clientRepository.findByIdentificationCard(reportMovement.getIdentificationCard());

        if(client == null){
            response.setMessage("Client not found");
            return response;
        }

        Map<String, Map<String, List<MovementsDTO.reportMovementResponse>>> mapClient = new HashMap<>();
        Map<String, List<MovementsDTO.reportMovementResponse>> mapMovements = new HashMap<>();


        List<Account> account = accountRepository.findByClient_IdClient(client.getIdClient());
        account.forEach(account1 -> {
            List<Movements> movements =  getMovementsForAccount(account1, reportMovement);
            List<MovementsDTO.reportMovementResponse> reportMovementResponses = new ArrayList<>();
            movements.forEach(movements1 -> {
                MovementsDTO.reportMovementResponse reportMovementResponse = new MovementsDTO.reportMovementResponse();
                reportMovementResponse.setMovementdate(movements1.getMovementdate());
                reportMovementResponse.setName(client.getName());
                reportMovementResponse.setAccountnumber(account1.getAccountnumber());
                reportMovementResponse.setMovementtype(movements1.getMovementtype());
                reportMovementResponse.setInitialbalance(account1.getInitialbalance());
                reportMovementResponse.setStatus(movements1.getStatus());
                reportMovementResponse.setMovementvalue(movements1.getMovementvalue());
                reportMovementResponse.setBalance(movements1.getBalance());
                reportMovementResponses.add(reportMovementResponse);
            });

            mapMovements.put(account1.getAccountnumber(), reportMovementResponses);
        });
        mapClient.put(client.getName()+"-"+client.getIdentificationCard(), mapMovements);
        data.put("report", mapClient);
        response.setData(data);
        response.setMessage("Report of movements");
        return response;

    }


    private List<Movements> getMovementsForAccount(Account account, MovementsDTO.reportMovement reportMovement) {
        try {
            return movementsRepository.findByAccount_IdaccountAndMovementdateBetweenOrderByMovementdateDesc(
                    account.getIdaccount(),
                    getStartOfDay(reportMovement.getDateInit()),
                    getEndOfDay(reportMovement.getDateEnd())
            );
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing dates", e);
        }
    }



}
