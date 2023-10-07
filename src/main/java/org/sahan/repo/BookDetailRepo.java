package org.sahan.repo;


import org.sahan.entity.BookDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookDetailRepo extends MongoRepository<BookDetail, Integer> {

}
