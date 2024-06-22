package com.bookstore.catalogservice.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final com.bookstore.catalogservice.domain.BookRepository bookRepository;

    public BookService(com.bookstore.catalogservice.domain.BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<com.bookstore.catalogservice.domain.Book> viewBookList() {
        return bookRepository.findAll();
    }

    public com.bookstore.catalogservice.domain.Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new com.bookstore.catalogservice.domain.BookNotFoundException(isbn));
    }

    public com.bookstore.catalogservice.domain.Book addBookToCatalog(com.bookstore.catalogservice.domain.Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new com.bookstore.catalogservice.domain.BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public com.bookstore.catalogservice.domain.Book editBookDetails(String isbn, com.bookstore.catalogservice.domain.Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new com.bookstore.catalogservice.domain.Book(
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price());
                    return bookRepository.save(bookToUpdate);
                })
                .orElseGet(() -> addBookToCatalog(book));
    }
}
