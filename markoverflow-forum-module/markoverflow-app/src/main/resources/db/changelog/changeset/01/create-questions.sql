--liquibase formatted sql

-- changeset Mark:create-questions

CREATE TABLE IF NOT EXISTS questions(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATE NOT NULL,
    reputation INTEGER,
    viewed INTEGER,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id)
)