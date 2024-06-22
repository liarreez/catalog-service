package com.bookstore.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book =
                new com.bookstore.catalogservice.domain.Book("1234567890", "Title", "Author", 9.90 );
        Set<ConstraintViolation<com.bookstore.catalogservice.domain.Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book =
                new com.bookstore.catalogservice.domain.Book("a234567890", "Title", "Author", 9.90);
        Set<ConstraintViolation<com.bookstore.catalogservice.domain.Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN format must be valid");
    }

    @Test
    void whenIsbnNotDefinedThenValidationFails() {
        var book =
                new com.bookstore.catalogservice.domain.Book(null , "Title", "Author", 9.90);
        Set<ConstraintViolation<com.bookstore.catalogservice.domain.Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book ISBN must be defined.");
    }

    @Test
    void whenTitleNotDefinedThenValidationFails() {
        var book =
                new com.bookstore.catalogservice.domain.Book("1234567890", null, "Author", 9.90 );
        Set<ConstraintViolation<com.bookstore.catalogservice.domain.Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book title must be defined.");
    }

    @Test
    void whenAuthorNotDefinedThenValidationFails() {
        var book =
                new com.bookstore.catalogservice.domain.Book("1234567890", "Title", null, 9.90 );
        Set<ConstraintViolation<com.bookstore.catalogservice.domain.Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book author must be defined.");
    }

    @Test
    void whenPriceDefinedButNegativeThenValidationFails() {
        var book =
                new com.bookstore.catalogservice.domain.Book("1234567890", "Title", "Author", -1.0 );
        Set<ConstraintViolation<com.bookstore.catalogservice.domain.Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be greater than zero");
    }

    @Test
    void whenPriceDefinedButZeroThenValidationFails() {
        var book =
                new com.bookstore.catalogservice.domain.Book("1234567890", "Title", "Author", 0.0 );
        Set<ConstraintViolation<com.bookstore.catalogservice.domain.Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be greater than zero");
    }
}
