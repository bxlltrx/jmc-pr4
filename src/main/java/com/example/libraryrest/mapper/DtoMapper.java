package com.example.libraryrest.mapper;

import com.example.libraryrest.dto.AuthorDto;
import com.example.libraryrest.dto.BookDto;
import com.example.libraryrest.model.Author;
import com.example.libraryrest.model.Book;

public class DtoMapper {

    private DtoMapper() {}

    public static AuthorDto toDto(Author a) {
        AuthorDto dto = new AuthorDto();
        dto.id = a.getId();
        dto.fullName = a.getFullName();
        dto.country = a.getCountry();
        dto.birthDate = a.getBirthDate();
        return dto;
    }

    public static BookDto toDto(Book b) {
        BookDto dto = new BookDto();
        dto.id = b.getId();
        dto.title = b.getTitle();
        dto.isbn = b.getIsbn();
        dto.publishedYear = b.getPublishedYear();

        dto.authorId = b.getAuthor().getId();
        dto.authorName = b.getAuthor().getFullName();
        return dto;
    }
}
