package com.sofka.movimientos.controllers;

import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.services.MovementsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/movements")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovementsControllers {

    private final MovementsService movementsService;

    @Operation(summary = "Creating new Movements")
    @PostMapping
    public ResponseEntity<Object> createMovements(@Valid @RequestBody MovementsDTO.createMovement movementsDTO) {
        return ResponseEntity.ok(movementsService.createMovement(movementsDTO));
    }

    @Operation(summary = "List info movements")
    @GetMapping
    public ResponseEntity<Object> listMovements() {
        return ResponseEntity.ok(movementsService.listMovement());
    }
}
