package com.StephenKing.Books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
      @JsonAlias("id") int id,
      @JsonAlias("Year")  int year,
      @JsonAlias("Title")  String title,
      @JsonAlias("Publisher")  String publisher,
      @JsonAlias("Pages")  int pages) {
}
