package com.sofka.movimientos.controllers;

import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sofka.movimientos.Utils.Util.validateInitialBalance;

@Slf4j
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountControllers {

    private final AccountService accountService;


    @Operation(summary = "Creating new account")
    @PostMapping
    public ResponseEntity<Object> createAccount(@Valid @RequestBody MovementsDTO.createAccount accountDTO) {
        validateInitialBalance(accountDTO.getInitialbalance());
        return ResponseEntity.ok(accountService.createAccount(accountDTO));
    }

    @Operation(summary = "List info account")
    @GetMapping
    public ResponseEntity<ResponseData> listAccount() {
        return ResponseEntity.ok(accountService.listAccount());
    }

    @Operation(summary = "Edit Account")
    @PutMapping
    public ResponseEntity<Object> editPasswordAccount(@Valid @RequestBody MovementsDTO.editAccount accountDTO) {
        return ResponseEntity.ok(accountService.editAccount(accountDTO));
    }

    @Operation(summary = "Delete info account")
    @DeleteMapping
    public ResponseEntity<Object> deleteAccount(@RequestParam String accountNumber) {
        return ResponseEntity.ok(accountService.deleteAccount(accountNumber));
    }
}