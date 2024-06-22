package com.bookstore.catalogservice.domain;

import java.util.Optional;

public interface BookRepository {

    Iterable<com.bookstore.catalogservice.domain.Book> findAll();

    Optional<com.bookstore.catalogservice.domain.Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    com.bookstore.catalogservice.domain.Book save(com.bookstore.catalogservice.domain.Book book);

    void deleteByIsbn(String isbn);
}
