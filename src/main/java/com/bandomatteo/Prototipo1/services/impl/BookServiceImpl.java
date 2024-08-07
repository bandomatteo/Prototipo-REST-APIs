package com.bandomatteo.Prototipo1.services.impl;

import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import com.bandomatteo.Prototipo1.repositories.BookRepository;
import com.bandomatteo.Prototipo1.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity bookToCreate) {
        //So we can be sure that the ISBN of the book we want to create will be exactly the same as the ISBN we are passing
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

    @Override
    public Optional<BookEntity> findOne(String isbn) {

        return bookRepository.findById(isbn);
    }

    @Override
    public boolean existsByIsbn(String isbn) {

        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
       bookEntity.setIsbn(isbn);

       return bookRepository.findById(isbn).map(existingBook -> {
           Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
           return bookRepository.save(existingBook);
       }).orElseThrow(()-> new RuntimeException("Book does not exists!"));
    }
}
