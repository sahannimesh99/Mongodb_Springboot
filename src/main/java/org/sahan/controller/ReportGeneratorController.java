package org.sahan.controller;

import org.sahan.entity.AuditConformation;
import org.sahan.service.ReportGeneratorService;
import org.sahan.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report-generator")
@CrossOrigin
public class ReportGeneratorController {
    private final ReportGeneratorService reportGeneratorService;

    public ReportGeneratorController(ReportGeneratorService reportGeneratorService) {
        this.reportGeneratorService = reportGeneratorService;
    }

    @GetMapping(path = "/get/values/report/{reportNumber}/client/{clientNumber}")
    public ResponseEntity<?> searchMappings(@PathVariable String reportNumber, @PathVariable String clientNumber) {
        AuditConformation addressDTO = reportGeneratorService.searchClient(reportNumber, clientNumber);
        return new ResponseEntity<>(new StandardResponse(200, "Success", addressDTO), HttpStatus.OK);
    }
}
