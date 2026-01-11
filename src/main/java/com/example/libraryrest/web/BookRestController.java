package com.example.libraryrest.web;

import com.example.libraryrest.dto.BookDto;
import com.example.libraryrest.dto.BooksXml;
import com.example.libraryrest.mapper.DtoMapper;
import com.example.libraryrest.model.Book;
import com.example.libraryrest.service.BookService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    // LIST (JSON)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookDto> listJson(@RequestParam(required = false) Long authorId) {
        var books = (authorId == null) ? bookService.findAll() : bookService.findByAuthorId(authorId);
        return books.stream().map(DtoMapper::toDto).toList();
    }

    // LIST (XML + XSL)
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> listXml(@RequestParam(required = false) Long authorId) {
        var books = (authorId == null) ? bookService.findAll() : bookService.findByAuthorId(authorId);
        var dtos = books.stream().map(DtoMapper::toDto).toList();
        String xml = XmlUtil.toXmlWithXsl(new BooksXml(dtos), "/xsl/books.xsl");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }

    // GET ONE (JSON)
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto getJson(@PathVariable Long id) {
        return DtoMapper.toDto(bookService.findById(id));
    }

    // GET ONE (XML + XSL)
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getXml(@PathVariable Long id) {
        BookDto dto = DtoMapper.toDto(bookService.findById(id));
        String xml = XmlUtil.toXmlWithXsl(dto, "/xsl/book.xsl");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
    }

    // CREATE
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> create(@RequestParam Long authorId, @RequestBody Book book) {
        var saved = bookService.create(book, authorId);
        var dto = DtoMapper.toDto(saved);
        return ResponseEntity.created(URI.create("/api/books/" + saved.getId())).body(dto);
    }

    // UPDATE
    @PutMapping(value="/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto update(@PathVariable Long id, @RequestParam Long authorId, @RequestBody Book book) {
        return DtoMapper.toDto(bookService.update(id, book, authorId));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
