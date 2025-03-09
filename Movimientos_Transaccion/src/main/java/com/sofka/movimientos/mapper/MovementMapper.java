package com.sofka.movimientos.mapper;

import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.entities.Account;
import com.sofka.movimientos.entities.Movements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class MovementMapper {
    public Movements toEntity(MovementsDTO.createMovement dto) {
        Movements movements = new Movements();
        Date date = new Date();
        movements.setMovementdate(date);






        movements.setMovementtype(dto.getMovementtype());
        movements.setMovementvalue(dto.getMovementvalue());
        movements.setBalance(calculateBalance(new Account(), movements));
        movements.setStatus(Boolean.TRUE);
        return movements;
    }

    private BigDecimal calculateBalance(Account account, Movements movements) {
        BigDecimal balance = account.getInitialbalance();
        if (movements.getMovementtype().equals("DEBIT")) {
            balance = balance.subtract(movements.getMovementvalue());
        } else {
            balance = balance.add(movements.getMovementvalue());
        }
        return balance;
    }
}
