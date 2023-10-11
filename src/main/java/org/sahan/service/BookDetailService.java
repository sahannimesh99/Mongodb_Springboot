package org.sahan.service;


import org.sahan.dto.AddressDTO;
import org.sahan.dto.BookDetailDTO;

import java.util.List;

public interface BookDetailService {
    void saveBookDetail(BookDetailDTO dto);

    void updateBookDetail(BookDetailDTO dto);

    BookDetailDTO searchBookDetail(Integer isbn);

    void deleteBookDetail(Integer isbn);

    List<BookDetailDTO> getAllBookDetail();

    List<AddressDTO> getAllClients();

    AddressDTO searchClient(String clientNumber);

    String createTemplate(String clientNumber);

    String convertHtmlToBase64(String clientNumber);
}
