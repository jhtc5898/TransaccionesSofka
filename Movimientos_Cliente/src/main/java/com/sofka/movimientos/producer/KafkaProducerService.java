package com.sofka.movimientos.producer;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducerService {

    private static final String TOPIC = "clientes-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void enviarCliente(String mensaje) {
        kafkaTemplate.send(TOPIC, mensaje);
        System.out.println("Cliente enviado a Kafka: " + mensaje);
    }
}
