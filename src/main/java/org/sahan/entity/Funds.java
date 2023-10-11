package org.sahan.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "Funds")
public class Funds {
    @Id
    private int id;
    private int NoOfUnits;
    private String fundName;
    private int unitPrice;
    private int marketValue;
    private int gains;
}
