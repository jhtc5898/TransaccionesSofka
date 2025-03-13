package com.sofka.movimientos.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.movimientos.dto.ClientKafkaDTO;
import com.sofka.movimientos.entities.Client;
import com.sofka.movimientos.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ClientRepository clientRepository;

    @KafkaListener(topics = "clientes-topic", groupId = "grupo-transacciones", containerFactory = "kafkaListenerContainerFactory")
    public void recibirCliente(String mensaje) {
        try {
            ClientKafkaDTO cliente = objectMapper.readValue(mensaje, ClientKafkaDTO.class);

            Client clienteNuevo = new Client();
            clienteNuevo.setIdClient(cliente.getIdClient());
            clienteNuevo.setName(cliente.getName());
            clienteNuevo.setGender(cliente.getGender());
            clienteNuevo.setAge(cliente.getAge());
            clienteNuevo.setIdentificationCard(cliente.getIdentificationCard());
            clienteNuevo.setDirection(cliente.getDirection());
            clienteNuevo.setPhone(cliente.getPhone());
            clienteNuevo.setStatus(cliente.getStatus());
            clienteNuevo.setPassword("password");

            log.info("Transacción recibió clienteNuevo: {}", clienteNuevo.toString());
            clientRepository.save(clienteNuevo);

        } catch (Exception e) {
            log.error("Error deserializando mensaje: {}", mensaje);
            e.printStackTrace();
        }
    }
}
