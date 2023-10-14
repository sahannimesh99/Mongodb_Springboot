package org.sahan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemplateReqDTO {
    private String reportId;
    private String reportName;
    private String htmlString;
}
