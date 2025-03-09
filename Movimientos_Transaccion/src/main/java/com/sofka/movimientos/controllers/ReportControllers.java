package com.sofka.movimientos.controllers;

import com.sofka.movimientos.Utils.ResponseData;
import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.exceptions.ErrorRequest;
import com.sofka.movimientos.services.MovementsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import static com.sofka.movimientos.Utils.Constants.CODE_ERROR_INTERNAL;

@Slf4j
@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class ReportControllers {

    @Autowired
    MovementsService movementsService;

    @Operation(summary = "Statement of account")
    @PostMapping
    public @ResponseBody
    ResponseEntity<byte[]> createMovements(@Valid @RequestBody() MovementsDTO.reportMovement reportMovement) {
        try {

            String reportContent = movementsService.reportMovement(reportMovement).toString();

            byte[] compressedReport = compressData(reportContent);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_ENCODING, "gzip")
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .body(compressedReport);
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest.toString().getBytes());
        }
    }

    @Operation(summary = "Statement of account")
    @PostMapping("/json")
    public @ResponseBody
    ResponseEntity<Object> createMovementsJSON(@Valid @RequestBody() MovementsDTO.reportMovement reportMovement) {
        try {
            return ResponseEntity.ok().body(movementsService.reportMovement(reportMovement));
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest.toString().getBytes());
        }
    }



    @Operation(summary = "Statement of account")
    @PostMapping("/jsonformat")
    public @ResponseBody
    ResponseEntity<Object> jsonformat(@Valid @RequestBody() MovementsDTO.reportMovement reportMovement) {
        try {
            return ResponseEntity.ok().body(movementsService.reportMovementFormat(reportMovement));
        } catch (Exception e) {
            ErrorRequest errorRequest = new ErrorRequest(e.getCause().getCause().getMessage(), CODE_ERROR_INTERNAL, e.getCause());
            return ResponseEntity.internalServerError().body(errorRequest.toString().getBytes());
        }
    }



    private byte[] compressData(String data) throws IOException {
        byte[] dataBytes = data.getBytes("UTF-8");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(dataBytes);
        }

        return byteArrayOutputStream.toByteArray();
    }

}
