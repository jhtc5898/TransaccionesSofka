package com.sofka.movimientos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovimientosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovimientosApplication.class, args);
    }

}
