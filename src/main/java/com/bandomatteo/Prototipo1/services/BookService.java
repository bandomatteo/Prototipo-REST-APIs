package com.bandomatteo.Prototipo1.services;

import com.bandomatteo.Prototipo1.domain.entities.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(String isbn,BookEntity bookToCreate);

    List<BookEntity> findAll();
}
