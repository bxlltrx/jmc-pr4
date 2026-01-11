DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS audit_log;

CREATE TABLE authors (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(255) NOT NULL,
  country VARCHAR(100),
  birth_date DATE
);

CREATE TABLE books (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  isbn VARCHAR(30) UNIQUE,
  published_year INT,
  author_id BIGINT NOT NULL,
  CONSTRAINT fk_books_author FOREIGN KEY (author_id) REFERENCES authors(id)
);


CREATE TABLE audit_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  event_type VARCHAR(20) NOT NULL,
  entity_type VARCHAR(50) NOT NULL,
  entity_id BIGINT,
  details VARCHAR(2000),
  created_at TIMESTAMP NOT NULL
);