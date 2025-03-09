package com.sofka.movimientos.controllers;

import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.exceptions.ErrorRequest;
import com.sofka.movimientos.services.AccountService;
import com.sofka.movimientos.services.MovementsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sofka.movimientos.Utils.Constants.CODE_ERROR_INTERNAL;

@Slf4j
@RestController
@RequestMapping("/movements")
@CrossOrigin(origins = "*")
public class MovementsControllers {

    @Autowired
    MovementsService movementsService;


    @Operation(summary = "Creating new Movements")
    @PostMapping
    public @ResponseBody
    ResponseEntity<Object> createMovements(@Valid @RequestBody() MovementsDTO.createMovement movementsDTO) {
        try {
            return ResponseEntity.ok().body(movementsService.createMovement(movementsDTO));
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest);
        }

    }

    @Operation(summary = "List info movements")
    @GetMapping
    public @ResponseBody
    ResponseEntity<Object> listMovements() {
        try {
            return ResponseEntity.ok().body(movementsService.listMovement());
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest);
        }

    }

}
