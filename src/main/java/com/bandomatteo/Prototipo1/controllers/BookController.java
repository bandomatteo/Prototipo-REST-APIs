package com.bandomatteo.Prototipo1.controllers;

import com.bandomatteo.Prototipo1.domain.dto.BookDto;
import com.bandomatteo.Prototipo1.domain.entities.BookEntity;
import com.bandomatteo.Prototipo1.mappers.Mapper;
import com.bandomatteo.Prototipo1.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private Mapper<BookEntity, BookDto> bookMapper;
    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable ("isbn") String isbn,
            @RequestBody BookDto bookDto){

        BookEntity bookEntity =  bookMapper.mapfrom(bookDto);

        BookEntity savedBookEntity = bookService.createBook(isbn,bookEntity);

        BookDto savedBookDto = bookMapper.mapto(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
    }
}
