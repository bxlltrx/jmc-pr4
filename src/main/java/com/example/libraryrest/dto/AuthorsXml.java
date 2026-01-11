package com.example.libraryrest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "authors")
public class AuthorsXml {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "author")
    public List<AuthorDto> authors;

    public AuthorsXml() {}
    public AuthorsXml(List<AuthorDto> authors) {
        this.authors = authors;
    }
}
