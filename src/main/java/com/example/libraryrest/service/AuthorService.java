package com.example.libraryrest.service;

import com.example.libraryrest.exception.NotFoundException;
import com.example.libraryrest.jms.ChangeEvent;
import com.example.libraryrest.model.Author;
import com.example.libraryrest.repo.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repo;
    private final ChangeEventPublisher publisher;

    public AuthorService(AuthorRepository repo, ChangeEventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    public List<Author> findAll() {
        return repo.findAll();
    }

    public Author findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found: " + id));
    }

    public Author create(Author author) {
        author.setId(null);
        Author saved = repo.save(author);

        publisher.publish(new ChangeEvent(
                "CREATE",
                "Author",
                saved.getId(),
                "Created author: fullName=" + saved.getFullName(),
                OffsetDateTime.now()
        ));

        return saved;
    }

    public Author update(Long id, Author author) {
        Author existing = findById(id);

        existing.setFullName(author.getFullName());
        existing.setCountry(author.getCountry());
        existing.setBirthDate(author.getBirthDate());

        Author saved = repo.save(existing);

        publisher.publish(new ChangeEvent(
                "UPDATE",
                "Author",
                saved.getId(),
                "Updated author: fullName=" + saved.getFullName() +
                        ", country=" + saved.getCountry() +
                        ", birthDate=" + saved.getBirthDate(),
                OffsetDateTime.now()
        ));

        return saved;
    }

    public void delete(Long id) {
        Author existing = findById(id);
        repo.deleteById(id);

        publisher.publish(new ChangeEvent(
                "DELETE",
                "Author",
                id,
                "Deleted author: fullName=" + existing.getFullName(),
                OffsetDateTime.now()
        ));
    }
}
