package com.sofka.movimientos.controllers;

import com.sofka.movimientos.dto.MovementsDTO;
import com.sofka.movimientos.services.MovementsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.sofka.movimientos.Utils.Util.compressData;

@Slf4j
@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReportControllers {

    private final MovementsService movementsService;

    @Operation(summary = "Statement of account (compressed)")
    @PostMapping
    public ResponseEntity<byte[]> createCompressedReport(@Valid @RequestBody MovementsDTO.reportMovement reportMovement) throws IOException {
        String reportContent = movementsService.reportMovement(reportMovement).toString();
        byte[] compressedReport = compressData(reportContent);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_ENCODING, "gzip")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(compressedReport);
    }

    @Operation(summary = "Statement of account (JSON)")
    @PostMapping("/json")
    public ResponseEntity<Object> createReportJSON(@Valid @RequestBody MovementsDTO.reportMovement reportMovement) {
        return ResponseEntity.ok(movementsService.reportMovement(reportMovement));
    }

    @Operation(summary = "Statement of account (formatted JSON)")
    @PostMapping("/jsonformat")
    public ResponseEntity<Object> createFormattedReport(@Valid @RequestBody MovementsDTO.reportMovement reportMovement) {
        return ResponseEntity.ok(movementsService.reportMovementFormat(reportMovement));
    }
}
