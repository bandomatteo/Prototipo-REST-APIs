package com.bandomatteo.Prototipo1.controllers;

import com.bandomatteo.Prototipo1.domain.dto.AuthorDto;
import com.bandomatteo.Prototipo1.domain.entities.AuthorEntity;
import com.bandomatteo.Prototipo1.mappers.Mapper;
import com.bandomatteo.Prototipo1.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorServices, Mapper<AuthorEntity, AuthorDto> authorMapper) {

        this.authorService = authorServices;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {

        AuthorEntity authorEntity = authorMapper.mapfrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);

        return new ResponseEntity<>(authorMapper.mapto(savedAuthorEntity), HttpStatus.CREATED);
    }
}
