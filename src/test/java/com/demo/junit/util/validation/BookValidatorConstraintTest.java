package com.demo.junit.util.validation;


import com.demo.junit.dto.request.CreateBookRequest;
import com.demo.junit.dto.response.AuthorResponse;
import com.demo.junit.model.Author;
import com.demo.junit.repository.AuthorRepository;
import com.demo.junit.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class BookValidatorConstraintTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Novak";
    private static final String NAME = "Victor";
    private static final AuthorResponse AUTHOR = new AuthorResponse();
    private static final LocalDate DATE = LocalDate.of(2010, 2, 2);
    private static final String DESCRIPTION = "Beautiful";
    private static final Author AUTHOR1 = new Author();
    private static final LocalDate BIRTHDAY = LocalDate.of(2015, 3, 10);
    private static final LocalDate DATE_NOW = LocalDate.now().plusDays(1);


    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;


    @InjectMocks
    private BookValidatorConstraint bookValidatorConstraint;

    @Test
    void isValidShouldReturnFalseWhenTitleIsNull() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle(null);

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);

//        then
        Assertions.assertFalse(actualResult);

    }

    @Test
    void isValidShouldReturnFalseWhenTitleIsEmpty() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("");

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);

//        then
        Assertions.assertFalse(actualResult);

    }

    @Test
    void isValidShouldReturnFalseWhenReleaseDateIsNull() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setReleaseDate(null);

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);

//        then
        Assertions.assertFalse(actualResult);

    }

    @Test
    void isValidShouldReturnFalseWhenReleaseDateIsAfterTodayDate() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setReleaseDate(DATE_NOW);

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);

//        then
        Assertions.assertFalse(actualResult);

    }

    @Test
    void isValidShouldReturnFalseWhenAuthorIsNull() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);


//        then
        Assertions.assertFalse(actualResult);

    }

    @Test
    void isValidShouldReturnFalseIfTitleExists() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);
        request.setAuthorId(ID);

        Mockito.when(bookRepository.existsByTitle(TITLE)).thenReturn(true);

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);


//        then
        Assertions.assertFalse(actualResult);

    }

    @Test
    void isValidShouldReturnFalseIfAuthorNotExists() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);
        request.setAuthorId(ID);

        Mockito.when(authorRepository.existsById(ID)).thenReturn(false);

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);


//        then
        Assertions.assertFalse(actualResult);

    }

    @Test
    void isValidShouldReturnTrue() {
//        given
        CreateBookRequest request = new CreateBookRequest();
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);
        request.setAuthorId(ID);

        Mockito.when(authorRepository.existsById(ID)).thenReturn(true);

//        when
        boolean actualResult = bookValidatorConstraint.isValid(request, null);


//        then
        Assertions.assertTrue(actualResult);

    }

}