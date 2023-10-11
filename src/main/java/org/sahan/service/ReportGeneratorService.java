package org.sahan.service;

import org.sahan.entity.AuditConformation;

public interface ReportGeneratorService {
    AuditConformation searchClient(String reportNumber, String clientNumber);
}
