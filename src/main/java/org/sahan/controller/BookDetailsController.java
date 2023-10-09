package org.sahan.controller;

import org.sahan.dto.AddressDTO;
import org.sahan.dto.BookDetailDTO;
import org.sahan.service.BookDetailService;
import org.sahan.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/book-detail")
@CrossOrigin
public class BookDetailsController {

    final
    BookDetailService bookDetailService;

    public BookDetailsController(BookDetailService bookDetailService) {
        this.bookDetailService = bookDetailService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addBookDetail(@RequestBody BookDetailDTO dto) {
        bookDetailService.saveBookDetail(dto);
        StandardResponse response = new StandardResponse(200, "Success", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteBookDetail(@RequestParam Integer isbn) {
        bookDetailService.deleteBookDetail(isbn);
        return new ResponseEntity<>(new StandardResponse(200, "Success", null), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateBookdetail(@RequestBody BookDetailDTO dto) {
        bookDetailService.updateBookDetail(dto);
        return new ResponseEntity<>(new StandardResponse(200, "Success", null), HttpStatus.OK);
    }

    @GetMapping(path = "/{isbn}")
    public ResponseEntity<?> searchCustomer(@PathVariable Integer isbn) {
        BookDetailDTO bookDetailDTO = bookDetailService.searchBookDetail(isbn);
        return new ResponseEntity<>(new StandardResponse(200, "Success", bookDetailDTO), HttpStatus.OK);
    }

    @GetMapping("/view-all")
    public ResponseEntity<?> getAllCustomers() {
        List<BookDetailDTO> allBookDetails = bookDetailService.getAllBookDetail();
        return new ResponseEntity<>(new StandardResponse(200, "Success", allBookDetails), HttpStatus.OK);
    }

    @GetMapping(path = "/get/clientNumbers")
    public ResponseEntity<?> getAllClients() {
        List<String> allClients = bookDetailService.getAllClients();
        return new ResponseEntity<>(new StandardResponse(200, "Success", allClients), HttpStatus.OK);
    }

    @GetMapping(path = "/{clientNumber}")
    public ResponseEntity<?> searchClient(@PathVariable String clientNumber) {
        AddressDTO addressDTO = bookDetailService.searchClient(clientNumber);
        return new ResponseEntity<>(new StandardResponse(200, "Success", addressDTO), HttpStatus.OK);
    }
}
