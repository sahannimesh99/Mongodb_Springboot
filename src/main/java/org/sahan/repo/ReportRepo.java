package org.sahan.repo;

import org.sahan.entity.TemplateReq;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepo extends MongoRepository<TemplateReq,String> {
}
