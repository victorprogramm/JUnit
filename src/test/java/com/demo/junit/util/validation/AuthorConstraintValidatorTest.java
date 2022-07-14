package com.demo.junit.util.validation;

import com.demo.junit.dto.request.CreateAuthorRequest;
import com.demo.junit.repository.AuthorRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AuthorConstraintValidatorTest {

  private static final String NAME = "Victor";
  private static final Long ID = 1L;
  private static final LocalDate BIRTHDAY = LocalDate.of(2015, 3, 10);

  @Mock
  AuthorRepository authorRepository;

  @InjectMocks
  AuthorConstraintValidator validator;

  @Test
  void isValidShouldReturnTrue() {

    // given
    CreateAuthorRequest request = new CreateAuthorRequest();
    request.setName(NAME);
    request.setBirthday(BIRTHDAY);

    when(authorRepository.existsByName(request.getName())).thenReturn(false);

    // when
    boolean isValid = validator.isValid(request, null);

    // then
    assertTrue(isValid);
  }

  @Test
  void isValidShouldReturnFalseWhenNameIsNull() {

    // given
    CreateAuthorRequest request = new CreateAuthorRequest();
    request.setName(null);
    request.setBirthday(BIRTHDAY);

    // when
    boolean isValid = validator.isValid(request, null);

    // then
    assertFalse(isValid);
  }

  @Test
  void isValidShouldReturnFalseWhenNameIsEmpty() {

    // given
    CreateAuthorRequest request = new CreateAuthorRequest();
    request.setName("");
    request.setBirthday(BIRTHDAY);

    // when
    boolean isValid = validator.isValid(request, null);

    // then
    assertFalse(isValid);
  }

  @Test
  void isValidShouldReturnFalseWhenBirthDayIsNull() {

    // given
    CreateAuthorRequest request = new CreateAuthorRequest();
    request.setName(NAME);
    request.setBirthday(null);

    // when
    boolean isValid = validator.isValid(request, null);

    // then
    assertFalse(isValid);
  }

  @Test
  void isValidShouldReturnFalseWhenNameIsExist() {

    // given
    CreateAuthorRequest request = new CreateAuthorRequest();
    request.setName(NAME);
    request.setBirthday(BIRTHDAY);

    when(authorRepository.existsByName(request.getName())).thenReturn(true);

    // when
    boolean isValid = validator.isValid(request, null);

    // then
    assertFalse(isValid);
  }
}