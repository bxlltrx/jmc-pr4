package com.example.libraryrest.repo;

import com.example.libraryrest.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> { }
