package com.bandomatteo.Prototipo1.services.impl;

import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import com.bandomatteo.Prototipo1.repositories.BookRepository;
import com.bandomatteo.Prototipo1.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.
                stream(bookRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
}
