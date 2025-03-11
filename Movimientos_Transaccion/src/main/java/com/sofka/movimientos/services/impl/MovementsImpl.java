package com.sofka.movimientos.services.impl;

import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.entities.Account;
import com.sofka.movimientos.entities.Client;
import com.sofka.movimientos.entities.Movements;
import com.sofka.movimientos.repositories.AccountRepository;
import com.sofka.movimientos.repositories.ClientRepository;
import com.sofka.movimientos.repositories.MovementsRepository;
import com.sofka.movimientos.services.MovementsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.sofka.movimientos.Utils.Constants.LIST_MOVEMENTS;
import static com.sofka.movimientos.Utils.Constants.SAVE_MOVEMENTS;
import static com.sofka.movimientos.Utils.Util.getEndOfDay;
import static com.sofka.movimientos.Utils.Util.getStartOfDay;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovementsImpl implements MovementsService {

    private final MovementsRepository movementsRepository;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public ResponseData createMovement(MovementsDTO.createMovement movementsDTO) {
        log.debug("Service to save a movements: {}", movementsDTO);
        ResponseData response = new ResponseData();

        Account account = accountRepository.findByAccountnumber(movementsDTO.getAccountnumber());
        if (!isValidAccount(account)) {
            return response.withError("Invalid account");
        }

        Movements lastMovement = movementsRepository.findTopByMovementtypeAndAccount_IdaccountOrderByMovementdateDesc(
                movementsDTO.getMovementtype(), account.getIdaccount());

        if (!isValidLastMovement(lastMovement)) {
            return response.withError("No valid previous movements found");
        }

        Movements newMovement = processNewMovement(movementsDTO, account, lastMovement);
        if (newMovement == null) {
            return response.withError("The balance is insufficient");
        }

        movementsRepository.save(newMovement);
        return response.withSuccess("Movements created successfully")
                .withData(SAVE_MOVEMENTS, Collections.singletonMap("movement", newMovement));
    }

    @Override
    public ResponseData listMovement() {
        log.debug("Listing all movements");
        List<Movements> movements = movementsRepository.findAll();
        return new ResponseData().withSuccess("List of movements").withData(LIST_MOVEMENTS, movements);
    }

    @Override
    public ResponseData reportMovement(MovementsDTO.reportMovement reportMovement) {
        return generateReport(reportMovement, false);
    }

    @Override
    public ResponseData reportMovementFormat(MovementsDTO.reportMovement reportMovement) {
        return generateReport(reportMovement, true);
    }

    private boolean isValidAccount(Account account) {
        return account != null && account.getStatus();
    }

    private boolean isValidLastMovement(Movements lastMovement) {
        return lastMovement != null && lastMovement.getStatus();
    }

    private Movements processNewMovement(MovementsDTO.createMovement movementsDTO, Account account, Movements lastMovement) {
        Movements newMovement = new Movements();
        newMovement.setMovementdate(new Date());
        newMovement.setMovementtype(movementsDTO.getMovementtype());
        newMovement.setMovementvalue(movementsDTO.getMovementvalue());
        BigDecimal newBalance = calculateBalance(lastMovement.getBalance(), newMovement.getMovementvalue());

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            return null;
        }

        newMovement.setBalance(newBalance);
        newMovement.setStatus(Boolean.TRUE);
        newMovement.setAccount(account);
        return newMovement;
    }

    private BigDecimal calculateBalance(BigDecimal oldBalance, BigDecimal movementValue) {
        return oldBalance.add(movementValue);
    }

    private ResponseData generateReport(MovementsDTO.reportMovement reportMovement, boolean formatted) {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();

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
