package com.bandomatteo.Prototipo1.services;

import com.bandomatteo.Prototipo1.domain.entities.AuthorEntity;

import java.util.List;


public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();
}
