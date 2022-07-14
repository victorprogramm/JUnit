package com.demo.junit.service;


import com.demo.junit.dto.request.CreateBookRequest;
import com.demo.junit.dto.request.UpdateBookRequest;
import com.demo.junit.dto.response.AuthorResponse;
import com.demo.junit.dto.response.BookResponse;
import com.demo.junit.mapper.BookMapper;
import com.demo.junit.model.Author;
import com.demo.junit.model.Book;
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
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private static final Long ID = 1L;
    private static final Long AUTHOR_ID = 2L;
    private static final String TITLE = "Novak";
    private static final String NAME = "Victor";
    private static final AuthorResponse AUTHOR = new AuthorResponse();
    private static final LocalDate DATE = LocalDate.of(2010, 2, 2);
    private static final String DESCRIPTION = "Beautiful";
    private static final Author AUTHOR1 = new Author();
    private static final LocalDate BIRTHDAY = LocalDate.of(2015, 3, 10);

    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void getById() {
        BookResponse expectedResult = new BookResponse();
        expectedResult.setId(ID);
        expectedResult.setAuthor(AUTHOR);
        expectedResult.setTitle(TITLE);
        expectedResult.setDescription(DESCRIPTION);
        expectedResult.setReleaseDate(DATE);

        Book book = new Book();
        book.setId(ID);
        book.setAuthor(AUTHOR1);
        book.setTitle(TITLE);
        book.setReleaseDate(DATE);
        book.setDescription(DESCRIPTION);

        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.of(book));

        Mockito.when(bookMapper.mapEntityToResponse(book)).thenReturn(expectedResult);

        BookResponse actualResult = bookService.getById(ID);

        Assertions.assertEquals(expectedResult.getId(), actualResult.getId());
    }

    @Test
    void getByIdException() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.getById(ID));

        Assertions.assertEquals(("Not found by id: " + ID), exception.getMessage());
    }

    @Test
    void getAll() {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(ID);
        bookResponse.setAuthor(AUTHOR);
        bookResponse.setTitle(TITLE);
        bookResponse.setDescription(DESCRIPTION);
        bookResponse.setReleaseDate(DATE);

        Book book = new Book();
        book.setId(ID);
        book.setAuthor(AUTHOR1);
        book.setTitle(TITLE);
        book.setReleaseDate(DATE);
        book.setDescription(DESCRIPTION);

        List <Book> books = List.of(book);

        List <BookResponse> expectedResult = List.of(bookResponse);

        Mockito.when(bookRepository.findAll()).thenReturn(books);
        Mockito.when(bookMapper.mapEntitiesToResponses(books)).thenReturn(expectedResult);

        List<BookResponse> actualResult = bookService.getAll();

        Assertions.assertEquals(expectedResult.size(), actualResult.size());
        Assertions.assertArrayEquals(expectedResult.toArray(), actualResult.toArray());

    }

    @Test
    void create() {
        CreateBookRequest request = new CreateBookRequest();
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);

        Book expectedResult = new Book();
        expectedResult.setId(ID);
        expectedResult.setAuthor(AUTHOR1);
        expectedResult.setTitle(TITLE);
        expectedResult.setReleaseDate(DATE);
        expectedResult.setDescription(DESCRIPTION);

        Mockito.when(bookMapper.mapCreateRequestToEntity(request)).thenReturn(expectedResult);
        Mockito.when(bookRepository.save(expectedResult)).thenReturn(expectedResult);

        Long actualResult = bookService.create(request);

        Assertions.assertEquals(ID, actualResult);
    }

    @Test
    void update() {
        Book book = new Book();
        book.setId(ID);
        book.setAuthor(AUTHOR1);
        book.setTitle(TITLE);
        book.setReleaseDate(DATE);
        book.setDescription(DESCRIPTION);

        UpdateBookRequest request = new UpdateBookRequest();
        request.setAuthorId(ID);
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);

        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        bookService.update(ID, request);

        Mockito.verify(bookRepository, Mockito.times(1)).findById(ID);

    }
    @Test
    void updateBookNotFound() {
        UpdateBookRequest request = new UpdateBookRequest();
        request.setAuthorId(ID);
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);

        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.update(ID, request));

        Assertions.assertEquals(("Book not found by id: " + ID), exception.getMessage());

    }

    @Test
    void changeAuthorForBook() {
        Author author = new Author();
        author.setId(AUTHOR_ID);
        author.setName(NAME);
        author.setBirthday(BIRTHDAY);

        Book book = new Book();
        book.setId(ID);
        book.setAuthor(AUTHOR1);
        book.setTitle(TITLE);
        book.setReleaseDate(DATE);
        book.setDescription(DESCRIPTION);

        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        Mockito.when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(author));

        bookService.changeAuthorForBook(ID, AUTHOR_ID);

        Mockito.verify(bookRepository, Mockito.times(1)).findById(ID);
        Mockito.verify(authorRepository, Mockito.times(1)).findById(AUTHOR_ID);

    }

    @Test
    void changeAuthorForBookException() {

        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.changeAuthorForBook(ID, AUTHOR_ID));

        Assertions.assertEquals(("Book not found by id: " + ID), exception.getMessage());

    }

    @Test
    void deleteById() {

        bookService.deleteById(ID);

        Mockito.verify(bookRepository, Mockito.times(1)).deleteById(ID);
    }
}