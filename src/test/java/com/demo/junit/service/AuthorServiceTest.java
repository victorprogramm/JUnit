package com.demo.junit.service;

import com.demo.junit.dto.request.CreateAuthorRequest;
import com.demo.junit.dto.response.AuthorResponse;
import com.demo.junit.mapper.AuthorMapper;
import com.demo.junit.model.Author;
import com.demo.junit.repository.AuthorRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AuthorServiceTest {

  private static final String NAME = "Victor";
  private static final Long ID = 1L;
  private static final LocalDate BIRTHDAY = LocalDate.of(2015, 3, 10);

  @Mock
  AuthorRepository authorRepository;

  @Mock
  AuthorMapper authorMapper;

  @InjectMocks
  AuthorService authorService;

  @Test
  void getById() {

    // given
    Author author = new Author();
    author.setId(ID);
    author.setName(NAME);
    author.setBirthday(BIRTHDAY);

    when(authorRepository.findById(ID)).thenReturn(Optional.of(author));

    AuthorResponse authorResponse = new AuthorResponse();
    authorResponse.setId(ID);
    authorResponse.setName(NAME);
    authorResponse.setBirthDate(BIRTHDAY);

    when(authorMapper.mapEntityToResponse(author)).thenReturn(authorResponse);

    // when
    AuthorResponse givenResult = authorService.getById(ID);

    // then
    assertEquals(authorResponse, givenResult);
  }

  @Test
  void getByIdShouldThrowRuntimeExceptionWhenNotFound() {

    // given
    when(authorRepository.findById(ID)).thenReturn(Optional.empty());

    // when
    RuntimeException exception = assertThrows(RuntimeException.class, () -> authorService.getById(ID));

    // then
    assertEquals("Not found by id: " + ID, exception.getMessage());
  }

  @Test
  void getAll() {

    // given
    Author author = new Author();
    author.setId(ID);
    author.setName(NAME);
    author.setBirthday(BIRTHDAY);

    List<Author> authors = List.of(author);

    when(authorRepository.findAll()).thenReturn(authors);

    AuthorResponse authorResponse = new AuthorResponse();
    authorResponse.setId(ID);
    authorResponse.setName(NAME);
    authorResponse.setBirthDate(BIRTHDAY);

    List<AuthorResponse> authorResponses = List.of(authorResponse);

    when(authorMapper.mapEntitiesToResponses(authors)).thenReturn(authorResponses);

    // when
    List<AuthorResponse> givenResults = authorService.getAll();

    // then
    assertEquals(authorResponses.size(), givenResults.size());
    assertArrayEquals(authorResponses.toArray(), givenResults.toArray());
  }

  @Test
  void create() {

    // given
    CreateAuthorRequest request = new CreateAuthorRequest();
    request.setName(NAME);
    request.setBirthday(BIRTHDAY);

    Author author = new Author();
    author.setName(NAME);
    author.setBirthday(BIRTHDAY);

    when(authorMapper.mapCreateRequestToEntity(request)).thenReturn(author);

    author.setId(ID);

    when(authorRepository.save(author)).thenReturn(author);

    // when
    Long id = authorService.create(request);

    // then
    assertEquals(ID, id);
    verify(authorRepository, times(1)).save(author);
  }

  @Test
  void deleteById() {
    authorService.deleteById(ID);
    verify(authorRepository, times(1)).deleteById(ID);
  }
}