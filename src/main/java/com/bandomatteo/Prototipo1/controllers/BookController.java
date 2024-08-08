package com.bandomatteo.Prototipo1.controllers;

import com.bandomatteo.Prototipo1.domain.dto.BookDto;
import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import com.bandomatteo.Prototipo1.mappers.Mapper;
import com.bandomatteo.Prototipo1.repositories.BookRepository;
import com.bandomatteo.Prototipo1.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable ("isbn") String isbn, @RequestBody BookDto bookDto){

        BookEntity bookEntity =  bookMapper.mapfrom(bookDto);

        boolean bookExists =  bookService.existsByIsbn(isbn);

        BookEntity savedBookEntity = bookService.createUpdateBook(isbn,bookEntity);

        BookDto savedBookDto = bookMapper.mapto(savedBookEntity);

        if (bookExists)
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK); // update
        else
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks(){
        List<BookEntity> books = bookService.findAll();

        return books.stream()
                .map(bookMapper::mapto)
                .collect(Collectors.toList());

    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBookbyIsbn(@PathVariable ("isbn") String isbn){

        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapto(bookEntity);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable ("isbn") String isbn, @RequestBody BookDto bookDto){

        if(!bookService.existsByIsbn(isbn))
            return new ResponseEntity<>(bookDto, HttpStatus.NOT_FOUND);

        BookEntity bookEntity =  bookMapper.mapfrom(bookDto);
        BookEntity updatedBookEntity = bookService.partialUpdate(isbn, bookEntity);

        return new ResponseEntity<>(bookMapper.mapto(updatedBookEntity), HttpStatus.OK);

    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable ("isbn") String isbn){

        bookService.delete(isbn);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
