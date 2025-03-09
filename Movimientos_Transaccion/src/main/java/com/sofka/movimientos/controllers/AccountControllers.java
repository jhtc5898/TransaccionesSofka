package com.sofka.movimientos.controllers;

import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.exceptions.ErrorRequest;
import com.sofka.movimientos.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sofka.movimientos.Utils.Constants.CODE_ERROR_INTERNAL;

@Slf4j
@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountControllers {

    @Autowired
    AccountService accountService;


    @Operation(summary = "Creating new account")
    @PostMapping
    public @ResponseBody
    ResponseEntity<Object> createAccount(@Valid @RequestBody() MovementsDTO.createAccount accountDTO) {
        try {
            return ResponseEntity.ok().body(accountService.createAccount(accountDTO));
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest);
        }
    }

    @Operation(summary = "List info account")
    @GetMapping
    public @ResponseBody
    ResponseEntity<Object> listAccount() {
        try {
            ResponseData responseData = accountService.listAccount();
            return ResponseEntity.ok().body(responseData);
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest);
        }

    }

    @Operation(summary = "Edit Account")
    @PutMapping
    public @ResponseBody
    ResponseEntity<Object> editPasswordAccount(@Valid @RequestBody() MovementsDTO.editAccount accountDTO) {
        try {
            return ResponseEntity.ok().body(accountService.editAccount(accountDTO));
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest);
        }
    }


    @Operation(summary = "Delete info account")
    @DeleteMapping
    public @ResponseBody
    ResponseEntity<Object> deleteAccount(@RequestParam String accountnumber) {
        try {
            return ResponseEntity.ok().body(accountService.deleteAccount(accountnumber));
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest);
        }
    }

}
