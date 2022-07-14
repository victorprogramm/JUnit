package com.demo.junit.mapper;

import com.demo.junit.dto.request.CreateBookRequest;
import com.demo.junit.dto.request.UpdateBookRequest;
import com.demo.junit.dto.response.AuthorResponse;
import com.demo.junit.dto.response.BookResponse;
import com.demo.junit.model.Author;
import com.demo.junit.model.Book;
import com.demo.junit.repository.AuthorRepository;
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
class BookMapperTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Novak";
    private static final String NAME = "Victor";
    private static final AuthorResponse AUTHOR = new AuthorResponse();
    private static final LocalDate DATE = LocalDate.of(2010, 2, 2);
    private static final String DESCRIPTION = "Beautiful";
    private static final Author AUTHOR1 = new Author();
    private static final LocalDate BIRTHDAY = LocalDate.of(2015, 3, 10);

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private BookMapper bookMapper;


    @Test
    void mapEntityToResponse() {
//        given
        Author author = new Author();
        author.setId(ID);
        author.setName(NAME);
        author.setBirthday(BIRTHDAY);

        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setId(ID);
        authorResponse.setName(NAME);
        authorResponse.setBirthDate(BIRTHDAY);

        Mockito.when(authorMapper.mapEntityToResponse(author)).thenReturn(authorResponse);

        Book book = new Book();
        book.setId(ID);
        book.setAuthor(author);
        book.setTitle(TITLE);
        book.setReleaseDate(DATE);
        book.setDescription(DESCRIPTION);

        BookResponse expectedResult = new BookResponse();
        expectedResult.setId(ID);
        expectedResult.setAuthor(authorResponse);
        expectedResult.setTitle(TITLE);
        expectedResult.setDescription(DESCRIPTION);
        expectedResult.setReleaseDate(DATE);

//        when
        BookResponse actualResult = bookMapper.mapEntityToResponse(book);

//        then
        Assertions.assertEquals(expectedResult.getId(), actualResult.getId());
        Assertions.assertEquals(expectedResult.getTitle(), actualResult.getTitle());
        Assertions.assertEquals(expectedResult.getDescription(), actualResult.getDescription());
        Assertions.assertEquals(expectedResult.getReleaseDate(), actualResult.getReleaseDate());
        Assertions.assertEquals(expectedResult.getAuthor(), actualResult.getAuthor());

    }

    @Test
    void mapEntitiesToResponses() {
        Book book = new Book();
        book.setId(ID);
        book.setAuthor(AUTHOR1);
        book.setTitle(TITLE);
        book.setReleaseDate(DATE);
        book.setDescription(DESCRIPTION);

        List<Book> books = List.of(book);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(ID);
        bookResponse.setAuthor(AUTHOR);
        bookResponse.setTitle(TITLE);
        bookResponse.setDescription(DESCRIPTION);
        bookResponse.setReleaseDate(DATE);

        List<BookResponse> expectedResult = List.of(bookResponse);


        List<BookResponse> actualResult = bookMapper.mapEntitiesToResponses(books);

        Assertions.assertEquals(expectedResult.size(), actualResult.size());


    }

    @Test
    void mapCreateRequestToEntity() {
        Author author = new Author();
        author.setId(ID);
        author.setName(NAME);
        author.setBirthday(BIRTHDAY);

        Book expectedResult = new Book();
        expectedResult.setId(ID);
        expectedResult.setAuthor(author);
        expectedResult.setTitle(TITLE);
        expectedResult.setReleaseDate(DATE);
        expectedResult.setDescription(DESCRIPTION);

        CreateBookRequest request = new CreateBookRequest();
        request.setAuthorId(ID);
        request.setDescription(DESCRIPTION);
        request.setTitle(TITLE);
        request.setReleaseDate(DATE);

        Mockito.when(authorRepository.findById(request.getAuthorId())).thenReturn(Optional.of(author));


        Book givenResult = bookMapper.mapCreateRequestToEntity(request);


        Assertions.assertEquals(expectedResult.getTitle(), givenResult.getTitle());
        Assertions.assertEquals(expectedResult.getDescription(), givenResult.getDescription());
        Assertions.assertEquals(expectedResult.getReleaseDate(), givenResult.getReleaseDate());
        Assertions.assertEquals(expectedResult.getAuthor(), givenResult.getAuthor());


    }

    @Test
    void updateEntityFromUpdateRequest() {
        Book book = new Book();
        book.setId(ID);
        book.setAuthor(AUTHOR1);
        book.setTitle(TITLE);
        book.setReleaseDate(DATE);
        book.setDescription(DESCRIPTION);

        UpdateBookRequest request = new UpdateBookRequest();
        request.setAuthorId(ID);
        request.setTitle(TITLE);
        request.setDescription(DESCRIPTION);
        request.setReleaseDate(DATE);

        Author author = new Author();
        author.setId(ID);
        author.setName(NAME);
        author.setBirthday(BIRTHDAY);

        Mockito.when(authorRepository.findById(request.getAuthorId())).thenReturn(Optional.of(author));

        bookMapper.updateEntityFromUpdateRequest(book, request);

        Assertions.assertEquals(book.getId(), request.getAuthorId());
        Assertions.assertEquals(book.getTitle(), request.getTitle());
        Assertions.assertEquals(book.getDescription(), request.getDescription());
        Assertions.assertEquals(book.getReleaseDate(), request.getReleaseDate());


    }


}