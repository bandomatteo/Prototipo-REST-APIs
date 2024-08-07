package com.bandomatteo.Prototipo1.services.impl;

import com.bandomatteo.Prototipo1.domain.entities.AuthorEntity;
import com.bandomatteo.Prototipo1.repositories.AuthorRepository;
import com.bandomatteo.Prototipo1.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity saveAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());


    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {

        authorEntity.setId(id); //this time we set the ID in the Service

        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(()-> new RuntimeException("Author does not exits with id: " + id));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }


//    // Set the ID of the author entity to the provided ID
//    authorEntity.setId(id);
//
//    // Find the existing author by ID
//    Optional<AuthorEntity> optionalExistingAuthor = authorRepository.findById(id);
//
//    // Check if the author exists
//    if (optionalExistingAuthor.isPresent()) {
//        AuthorEntity existingAuthor = optionalExistingAuthor.get();
//
//        // Update the fields if they are not null
//        if (authorEntity.getName() != null) {
//            existingAuthor.setName(authorEntity.getName());
//        }
//        if (authorEntity.getAge() != null) {
//            existingAuthor.setAge(authorEntity.getAge());
//        }
//
//        // Save the updated author entity
//        return authorRepository.save(existingAuthor);
//    } else {
//        // Throw an exception if the author does not exist
//        throw new RuntimeException("Author does not exist with id: " + id);
//    }
}
