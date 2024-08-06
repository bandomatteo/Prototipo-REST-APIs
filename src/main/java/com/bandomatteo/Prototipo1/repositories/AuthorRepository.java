package com.bandomatteo.Prototipo1.repositories;

import com.bandomatteo.Prototipo1.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
