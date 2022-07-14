package com.demo.junit.dto.request;

import com.demo.junit.util.validation.BookValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@BookValidator
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBookRequest {

  String title;
  LocalDate releaseDate;
  String description;
  Long authorId;
}
