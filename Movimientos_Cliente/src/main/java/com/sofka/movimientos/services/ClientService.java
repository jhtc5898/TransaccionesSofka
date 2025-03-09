package com.sofka.movimientos.services;

import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.ClientDTO;

public interface ClientService {

    ResponseData createClient(ClientDTO.createClient clientDTO);

    ResponseData listClient();

    ResponseData editPasswordClient(ClientDTO.editClient clientDTO);

    ResponseData deleteClient(String identificationCard);


}
