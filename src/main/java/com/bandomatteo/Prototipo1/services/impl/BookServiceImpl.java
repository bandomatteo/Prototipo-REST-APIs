package com.bandomatteo.Prototipo1.services.impl;

import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import com.bandomatteo.Prototipo1.repositories.BookRepository;
import com.bandomatteo.Prototipo1.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookToCreate) {

        bookToCreate.setIsbn(isbn);
        return bookRepository.save(bookToCreate);
    }
}
