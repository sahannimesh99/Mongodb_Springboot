package org.sahan.controller;

import org.bson.Document;
import org.sahan.dto.TemplateReqDTO;
import org.sahan.dto.TemplateResDTO;
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

    @GetMapping(path = "/get/values/report/{reportNumber}/client/{clientNumber}/db")
    public ResponseEntity<?> searchMappingsInDb(@PathVariable String reportNumber, @PathVariable String clientNumber) {
        Document s = reportGeneratorService.searchClientFromDb(reportNumber, clientNumber);
        return new ResponseEntity<>(new StandardResponse(200, "Success", s), HttpStatus.OK);
    }

    @PostMapping("/report/add")
    public ResponseEntity<?> addJsonData(@RequestBody TemplateReqDTO dto) {
        reportGeneratorService.saveJson(dto);
        StandardResponse response = new StandardResponse(200, "Success", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/get/values/report/{reportNumber}/client/{clientNumber}/template/res")
    public ResponseEntity<?> sendTemplateResponse(@PathVariable String reportNumber, @PathVariable String clientNumber) {
        TemplateResDTO s = reportGeneratorService.sendTemplateRes(reportNumber, clientNumber);
        return new ResponseEntity<>(new StandardResponse(200, "Success", s), HttpStatus.OK);
    }
}
