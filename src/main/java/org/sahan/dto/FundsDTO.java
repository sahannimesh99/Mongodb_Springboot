package org.sahan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FundsDTO {
    private String fundName;
    private int NoOfUnits;
    private int unitPrice;
    private int marketValue;
    private int gains;

}
