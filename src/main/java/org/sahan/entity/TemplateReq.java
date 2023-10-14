package org.sahan.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "TemplateReq")
public class TemplateReq {
    @Id
    private String reportId;
    private String reportName;
    private String htmlString;
}
