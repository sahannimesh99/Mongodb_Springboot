package org.sahan.repo;

import org.sahan.entity.AuditConformation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportGeneratorRepo extends MongoRepository<AuditConformation,String> {

}
