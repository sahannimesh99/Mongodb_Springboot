package org.sahan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDetailDTO {
    private int isbn;
    private String name;
    private String author;
    private String category;
}
