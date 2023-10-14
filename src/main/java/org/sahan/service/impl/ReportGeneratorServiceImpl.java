package org.sahan.service.impl;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.modelmapper.ModelMapper;
import org.sahan.dto.TemplateReqDTO;
import org.sahan.dto.TemplateResDTO;
import org.sahan.entity.AuditConformation;
import org.sahan.entity.TemplateReq;
import org.sahan.repo.ReportGeneratorRepo;
import org.sahan.repo.ReportRepo;
import org.sahan.service.ReportGeneratorService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class ReportGeneratorServiceImpl implements ReportGeneratorService {
    private final ReportGeneratorRepo reportGeneratorRepo;
    private final ReportRepo reportRepo;
    private final ModelMapper mapper;

    public ReportGeneratorServiceImpl(ReportGeneratorRepo reportGeneratorRepo, ModelMapper mapper, ReportRepo reportRepo) {
        this.reportGeneratorRepo = reportGeneratorRepo;
        this.mapper = mapper;
        this.reportRepo = reportRepo;
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

    @Override
    public Document searchClientFromDb(String reportName, String clientNumber) {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("BookStore");

            boolean collectionExists = database.listCollectionNames().into(new ArrayList<>()).contains(reportName);

            if (collectionExists) {
                System.out.println("Collection exists: " + reportName);

                // Get the collection
                var collection = database.getCollection(reportName);

                String keyToFind = "clientNumber";

                // Create a filter to find the document with the specified key and value
                var filter = new Document(keyToFind, clientNumber);

                // Find the document in the collection
                var result = collection.find(filter).first();

                if (result != null) {
                    System.out.println("Found document: " + result.toJson());
                    return result;
                } else {
                    System.out.println("Document not found with key " + keyToFind + " and value " + clientNumber);
                }
            } else {
                System.out.println("Collection does not exist: " + reportName);
            }
        }
        throw new RuntimeException("Document not found");
    }

    @Override
    public void saveJson(TemplateReqDTO dto) {
        if (!reportRepo.existsById(dto.getReportId())) {
            TemplateReq c = mapper.map(dto, TemplateReq.class);
            reportRepo.save(c);
        } else {
            throw new RuntimeException("Book already exist..!");
        }
    }

    @Override
    public TemplateResDTO sendTemplateRes(String reportName, String clientNumber) {
        TemplateResDTO res = new TemplateResDTO();
//        res.setTemplateHtml();
        res.setDataJson(searchClientFromDb(reportName, clientNumber).toJson());
        System.out.println(searchClientFromDb(reportName, clientNumber).toJson());
        return res;
    }

}
