package com.sofka.movimientos.client;

import com.sofka.movimientos.entities.Client;
import com.sofka.movimientos.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@Slf4j
public class ClientTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        log.info("Configuración inicial completada: Mocks inicializados.");
    }

    @Test
    void testClientCreation() {
        log.info("Iniciando prueba: testClientCreation simulando el comportamiento de las dependencias de ClientRepository");

        UUID id = UUID.randomUUID();
        String name = "John Doe";
        String identificationCard = "1234567890";
        String direction = "123 Main St";
        String password = "password123";
        Boolean status = true;

        client.setIdClient(id);
        client.setName(name);
        client.setIdentificationCard(identificationCard);
        client.setDirection(direction);
        client.setPassword(password);
        client.setStatus(status);

        log.debug("Datos del cliente creado: {}", client);


        when(clientRepository.save(client)).thenReturn(client);


        Client savedClient = clientRepository.save(client);

        log.debug("Cliente guardado en el repositorio: {}", savedClient);


        assertNotNull(savedClient, "El cliente guardado no debe ser nulo");
        assertEquals(id, savedClient.getIdClient(), "El ID del cliente no coincide");
        assertEquals(name, savedClient.getName(), "El nombre del cliente no coincide");
        assertEquals(identificationCard, savedClient.getIdentificationCard(), "La cédula del cliente no coincide");
        assertEquals(direction, savedClient.getDirection(), "La dirección del cliente no coincide");
        assertEquals(password, savedClient.getPassword(), "La contraseña del cliente no coincide");
        assertEquals(status, savedClient.getStatus(), "El estado del cliente no coincide");

        log.info("Prueba completada: testClientCreation");
    }

    @Test
    void testFindClientByIdentificationCard() {
        log.info("Iniciando prueba: testFindClientByIdentificationCard");

        String identificationCard = "1234567890";
        Client mockClient = new Client();
        mockClient.setIdentificationCard(identificationCard);

        log.debug("Cliente mock creado: {}", mockClient);

        when(clientRepository.findByIdentificationCard(identificationCard)).thenReturn(mockClient);

        Client foundClient = clientRepository.findByIdentificationCard(identificationCard);

        log.debug("Cliente encontrado: {}", foundClient);


        assertNotNull(foundClient, "El cliente encontrado no debe ser nulo");
        assertEquals(identificationCard, foundClient.getIdentificationCard(), "La cédula del cliente no coincide");

        log.info("Prueba completada: testFindClientByIdentificationCard");
    }
}
