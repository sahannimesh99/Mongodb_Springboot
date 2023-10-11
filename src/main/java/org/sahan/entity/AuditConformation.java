package org.sahan.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "AuditConformation")
public class AuditConformation {
    @Id
    private String clientNumber;
    private String date;
    private String toAddress;
    private String clientName;
    private List<Funds> funds;
    private String referenceName;
    private String ccAddress;
}
