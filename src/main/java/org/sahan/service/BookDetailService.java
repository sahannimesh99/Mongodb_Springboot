package org.sahan.service;


import org.sahan.dto.AuditConformationDTO;
import org.sahan.dto.BookDetailDTO;
import org.sahan.dto.TemplateResDTO;

import java.util.List;

public interface BookDetailService {
    void saveBookDetail(BookDetailDTO dto);

    void saveJson(AuditConformationDTO dto);

    void updateBookDetail(BookDetailDTO dto);

    BookDetailDTO searchBookDetail(Integer isbn);

    void deleteBookDetail(Integer isbn);

    List<BookDetailDTO> getAllBookDetail();

    List<AuditConformationDTO> getAllClients();

    AuditConformationDTO searchClient(String clientNumber);

    String createTemplate(String clientNumber);

    String convertHtmlToBase64(String clientNumber);


}
