package com.sofka.movimientos.repositories;

import com.sofka.movimientos.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByAccountnumber(String accountNumber);

    List<Account> findByClient_IdClient(UUID idClient);


}
