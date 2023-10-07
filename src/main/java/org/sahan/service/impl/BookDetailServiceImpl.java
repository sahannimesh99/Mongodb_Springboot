package org.sahan.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sahan.dto.BookDetailDTO;
import org.sahan.entity.BookDetail;
import org.sahan.repo.BookDetailRepo;
import org.sahan.service.BookDetailService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookDetailServiceImpl implements BookDetailService {
    private final BookDetailRepo repo;

    private final ModelMapper mapper;

    public BookDetailServiceImpl(BookDetailRepo repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public void saveBookDetail(BookDetailDTO dto) {
        if (!repo.existsById(dto.getIsbn())) {
            BookDetail c = mapper.map(dto, BookDetail.class);
            repo.save(c);
        } else {
            throw new RuntimeException("Book already exist..!");
        }
    }

    @Override
    public void updateBookDetail(BookDetailDTO dto) {
        if (repo.existsById(dto.getIsbn())) {
            BookDetail c = mapper.map(dto, BookDetail.class);
            repo.save(c);
        } else {
            throw new RuntimeException("No such book for update..!");
        }
    }

    @Override
    public BookDetailDTO searchBookDetail(Integer isbn) {
        Optional<BookDetail> bookDetail = repo.findById(isbn);
        if (bookDetail.isPresent()) {
            return mapper.map(bookDetail.get(), BookDetailDTO.class);
        } else {
            throw new RuntimeException("No book for id: " + isbn);
        }
    }


    @Override
    public void deleteBookDetail(Integer isbn) {
        if (repo.existsById(isbn)) {
            repo.deleteById(isbn);
        } else {
            throw new RuntimeException("No customer for delete ID: " + isbn);
        }

    }

    @Override
    public List<BookDetailDTO> getAllBookDetail() {
        List<BookDetail> all = repo.findAll();
        return mapper.map(all, new TypeToken<List<BookDetailDTO>>() {
        }.getType());
    }

}

