package com.example.libraryrest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "book")
public class BookDto {
    public Long id;
    public String title;
    public String isbn;
    public Integer publishedYear;

    public Long authorId;
    public String authorName;
}
