package com.example.libraryrest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;

@JacksonXmlRootElement(localName = "author")
public class AuthorDto {
    public Long id;
    public String fullName;
    public String country;
    public LocalDate birthDate;
}
