package org.sahan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AuditConformationDTO {
    private String date;
    private String toAddress;
    private String clientName;
    private String clientNumber;
    private List<FundsDTO> funds;
    private String referenceName;
    private String ccAddress;
}
