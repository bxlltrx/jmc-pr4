package com.example.libraryrest.service;

import com.example.libraryrest.exception.NotFoundException;
import com.example.libraryrest.jms.ChangeEvent;
import com.example.libraryrest.model.Author;
import com.example.libraryrest.model.Book;
import com.example.libraryrest.repo.BookRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;
    private final AuthorService authorService;
    private final ChangeEventPublisher publisher;

    public BookService(BookRepository repo, AuthorService authorService, ChangeEventPublisher publisher) {
        this.repo = repo;
        this.authorService = authorService;
        this.publisher = publisher;
    }

    public List<Book> findAll() {
        return repo.findAll();
    }

    public List<Book> findByAuthorId(Long authorId) {
        return repo.findByAuthorId(authorId);
    }

    public Book findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found: " + id));
    }

    public Book create(Book book, Long authorId) {
        Author author = authorService.findById(authorId);
        book.setId(null);
        book.setAuthor(author);

        Book saved = repo.save(book);

        publisher.publish(new ChangeEvent(
                "CREATE",
                "Book",
                saved.getId(),
                "Created book: title=" + saved.getTitle() + ", authorId=" + authorId,
                OffsetDateTime.now()
        ));

        return saved;
    }

    public Book update(Long id, Book book, Long authorId) {
        Book existing = findById(id);
        Author author = authorService.findById(authorId);

        existing.setTitle(book.getTitle());
        existing.setIsbn(book.getIsbn());
        existing.setPublishedYear(book.getPublishedYear());
        existing.setAuthor(author);

        Book saved = repo.save(existing);

        publisher.publish(new ChangeEvent(
                "UPDATE",
                "Book",
                saved.getId(),
                "Updated book: title=" + saved.getTitle() +
                        ", isbn=" + saved.getIsbn() +
                        ", publishedYear=" + saved.getPublishedYear() +
                        ", authorId=" + authorId,
                OffsetDateTime.now()
        ));

        return saved;
    }

    public void delete(Long id) {
        Book existing = findById(id);
        repo.deleteById(id);

        publisher.publish(new ChangeEvent(
                "DELETE",
                "Book",
                id,
                "Deleted book: title=" + existing.getTitle(),
                OffsetDateTime.now()
        ));
    }
}
