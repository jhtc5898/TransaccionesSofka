package com.sofka.movimientos.repositories;

import com.sofka.movimientos.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByIdentificationCard(String identificationCard);

    List<Client> findByStatusTrue();

    @Query("SELECT c FROM Client c WHERE c.status = true")
    List<Client> findClients();



}
