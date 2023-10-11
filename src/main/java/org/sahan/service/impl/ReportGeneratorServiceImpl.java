package org.sahan.service.impl;

import org.sahan.entity.AuditConformation;
import org.sahan.repo.ReportGeneratorRepo;
import org.sahan.service.ReportGeneratorService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    private final ReportGeneratorRepo reportGeneratorRepo;

    public ReportGeneratorServiceImpl(ReportGeneratorRepo reportGeneratorRepo) {
        this.reportGeneratorRepo = reportGeneratorRepo;
    }

    @Override
    public AuditConformation searchClient(String reportNumber, String clientNumber) {
        Optional<AuditConformation> auditConformation = reportGeneratorRepo.findById(clientNumber);
        if (auditConformation.isPresent()) {
            return auditConformation.get();
        } else {
            throw new RuntimeException("No client for id: " + clientNumber);
        }
    }
}
