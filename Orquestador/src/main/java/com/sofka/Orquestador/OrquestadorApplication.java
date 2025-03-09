package com.sofka.Orquestador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrquestadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrquestadorApplication.class, args);
    }

}
