package com.example.libraryrest.web;

import com.example.libraryrest.dto.AuthorDto;
import com.example.libraryrest.dto.AuthorsXml;
import com.example.libraryrest.mapper.DtoMapper;
import com.example.libraryrest.model.Author;
import com.example.libraryrest.service.AuthorService;
import com.example.libraryrest.service.BookService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorRestController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    // -------- LIST (JSON) --------
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AuthorDto> listJson() {
        return authorService.findAll().stream().map(DtoMapper::toDto).toList();
    }

    // -------- LIST (XML + XSL) --------
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> listXml() {
        var dtos = authorService.findAll().stream().map(DtoMapper::toDto).toList();
        String xml = XmlUtil.toXmlWithXsl(new AuthorsXml(dtos), "/xsl/authors.xsl");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }

    // -------- GET ONE (JSON) --------
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDto getJson(@PathVariable Long id) {
        return DtoMapper.toDto(authorService.findById(id));
    }

    // -------- GET ONE (XML + XSL) --------
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXml(@PathVariable Long id) {
        AuthorDto dto = DtoMapper.toDto(authorService.findById(id));
        String xml = XmlUtil.toXmlWithXsl(dto, "/xsl/author.xsl");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }

    // -------- CREATE --------
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> create(@RequestBody Author author) {
        Author saved = authorService.create(author);
        AuthorDto dto = DtoMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/api/authors/" + saved.getId())).body(dto);
    }

    // -------- UPDATE --------
    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDto update(@PathVariable Long id, @RequestBody Author author) {
        return DtoMapper.toDto(authorService.update(id, author));
    }

    // -------- DELETE --------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // -------- BOOKS OF AUTHOR (JSON) --------
    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object authorBooksJson(@PathVariable Long id) {
        // убедимся, что автор существует
        authorService.findById(id);
        return bookService.findByAuthorId(id).stream().map(DtoMapper::toDto).toList();
    }

    // -------- BOOKS OF AUTHOR (XML + XSL) --------
    @GetMapping(value = "/{id}/books", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> authorBooksXml(@PathVariable Long id) {
        authorService.findById(id);
        var dtos = bookService.findByAuthorId(id).stream().map(DtoMapper::toDto).toList();
        String xml = XmlUtil.toXmlWithXsl(new com.example.libraryrest.dto.BooksXml(dtos), "/xsl/books.xsl");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }
}
