package org.sahan.service;

import org.bson.Document;
import org.sahan.dto.TemplateReqDTO;
import org.sahan.dto.TemplateResDTO;
import org.sahan.entity.AuditConformation;

public interface ReportGeneratorService {
    AuditConformation searchClient(String reportNumber, String clientNumber);

    Document searchClientFromDb(String reportName, String clientNumber);

    void saveJson(TemplateReqDTO templateReqDTO);

    TemplateResDTO sendTemplateRes(String reportName, String clientNumber);

}
