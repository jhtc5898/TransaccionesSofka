package com.sofka.movimientos.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sofka.movimientos.dto.ClientDTO;
import com.sofka.movimientos.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientControllers {

    @Autowired
    ClientService clientService;

    @Operation(summary = "Creating new client")
    @PostMapping
    public @ResponseBody
    ResponseEntity<Object> createClient(@Valid @RequestBody() ClientDTO.createClient clientDTO) throws JsonProcessingException {
            return ResponseEntity.ok().body(clientService.createClient(clientDTO));
    }

    @Operation(summary = "List info client")
    @GetMapping
    public @ResponseBody
    ResponseEntity<Object> listClient() {
            return ResponseEntity.ok().body(clientService.listClient());
    }

    @Operation(summary = "Edit password client")
    @PutMapping
    public @ResponseBody
    ResponseEntity<Object> editPasswordClient(@Valid @RequestBody() ClientDTO.editClient clientDTO) {
        return ResponseEntity.ok().body(clientService.editPasswordClient(clientDTO));
    }


    @Operation(summary = "Delete info client")
    @DeleteMapping
    public @ResponseBody
    ResponseEntity<Object> deleteClient(@RequestParam String identificationCard) {
            return ResponseEntity.ok().body(clientService.deleteClient(identificationCard));
    }
}
