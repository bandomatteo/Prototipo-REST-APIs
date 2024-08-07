package com.bandomatteo.Prototipo1.services;

import com.bandomatteo.Prototipo1.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn,BookEntity bookToCreate);
}
