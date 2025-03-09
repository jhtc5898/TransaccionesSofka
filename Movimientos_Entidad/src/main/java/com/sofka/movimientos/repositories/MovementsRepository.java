package com.sofka.movimientos.repositories;

import com.sofka.movimientos.entities.Account;
import com.sofka.movimientos.entities.Movements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MovementsRepository extends JpaRepository<Movements, Long> {
    Movements findByAccount(Account account);

    Movements findTopByMovementtypeAndAccount_IdaccountOrderByMovementdateDesc(String movementtype, UUID idaccount);

    List<Movements> findByAccount_IdaccountAndMovementdateBetweenOrderByMovementdateDesc(UUID idaccount, Date startDate, Date endDate);
}
