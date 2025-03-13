package com.sofka.movimientos.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.ClientDTO;
import com.sofka.movimientos.dto.ClientKafkaDTO;
import com.sofka.movimientos.entities.Client;
import com.sofka.movimientos.producer.KafkaProducerService;
import com.sofka.movimientos.repositories.ClientRepository;
import com.sofka.movimientos.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.sofka.movimientos.Utils.Constants.*;


@Slf4j
@Service
public class ClientImpl implements ClientService {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private KafkaProducerService kafkaProducer;

    @Override
    @Transactional
    public ResponseData createClient(ClientDTO.createClient clientDTO) throws JsonProcessingException {
       ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("Service to save a client : {}", clientDTO);

        // Crea un cliente nuevo
        Client client = new Client();
        client.setIdentificationCard(clientDTO.getIdentificationCard());
        client.setName(clientDTO.getName());
        client.setGender(clientDTO.getGender());
        client.setAge(clientDTO.getAge());
        client.setDirection(clientDTO.getDirection());
        client.setPhone(clientDTO.getPhone());

        // Encriptar la contraseña antes de almacenarla
        String encryptedPassword = passwordEncoder.encode(clientDTO.getPassword());
        client.setPassword(encryptedPassword);

        client.setStatus(Boolean.TRUE);

        // Guardar el cliente
        clientRepository.save(client);


        data.put(SAVE_CLIENT, client);

        System.out.println("Cliente creado: " + client);
        ClientKafkaDTO clientKafka = new ClientKafkaDTO(client.getIdClient(), client.getName(), client.getGender(), client.getAge(), client.getIdentificationCard(), client.getDirection(), client.getPhone(), true);

        String mensaje = objectMapper.writeValueAsString(clientKafka);


        kafkaProducer.enviarCliente(mensaje);
        response.setMessage("Client created successfully");
        response.setData(data);
        return response;
    }



    @Override
    public ResponseData listClient() {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("List Info client");
        data.put(LIST_CLIENT, clientRepository.findAll());
        if (data.isEmpty()) {
            data.put(LIST_CLIENT, new ArrayList<>());
        }

        response.setData(data);
        response.setMessage("List of clients");
        return response;
    }

    @Override
    @Transactional
    public ResponseData editPasswordClient(ClientDTO.editClient clientDTO) {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("Editing client password: {}", clientDTO);


        Client client = clientRepository.findByIdentificationCard(clientDTO.getIdentificationCard());


        if (client == null) {
            response.setMessage("Client not found");
            return response;
        }

        if (!client.getStatus()) {
            response.setMessage("Client is disabled");
            return response;
        }


        boolean isPasswordUpdated = updateClientPassword(client, clientDTO);


        if (isPasswordUpdated) {
            clientRepository.save(client);
            data.put(EDIT_CLIENT, client);
            response.setMessage("Password updated successfully");
        } else {
            response.setMessage("Old password is incorrect");
        }

        response.setData(data);
        return response;
    }

    private boolean updateClientPassword( Client client,ClientDTO.editClient clientDTO) {

        if (clientDTO.getPasswordOld() != null && !clientDTO.getPasswordOld().isEmpty()) {
            if(passwordEncoder.matches(clientDTO.getPasswordOld(), client.getPassword())) {
                String encryptedPassword = passwordEncoder.encode(clientDTO.getPasswordNew());
                client.setPassword(encryptedPassword);
                return true;
            } else {
                return false;
            }

        }
        return false;
    }



    @Override
    @Transactional
    public ResponseData deleteClient(String identificationCard) {
        ResponseData response = new ResponseData();
        Map<String, Object> data = new HashMap<>();
        log.debug("Delete client : {}", identificationCard);

        Client client = clientRepository.findByIdentificationCard(identificationCard);

        if (client == null) {
            response.setMessage("Client not found");
            return response;
        }


        if (client.getStatus()) {
            client.setStatus(Boolean.FALSE);
            data.put(DELETE_CLIENT, Boolean.TRUE);
            response.setMessage("Client deleted successfully");
            response.setData(data);

        } else {
            response.setMessage("Client is already disabled");
        }

        return response;
    }
}
