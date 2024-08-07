package com.bandomatteo.Prototipo1.services;

import com.bandomatteo.Prototipo1.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createUpdateBook(String isbn, BookEntity bookToCreate);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean existsByIsbn(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);
}
