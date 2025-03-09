package com.sofka.movimientos.repositories;

import com.sofka.movimientos.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByIdentificationCard(String identificationCard);




}
