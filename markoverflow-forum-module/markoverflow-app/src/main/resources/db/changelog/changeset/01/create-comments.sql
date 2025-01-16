--liquibase formatted sql

-- changeset Mark:create-comments

CREATE TABLE IF NOT EXISTS comments(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    content TEXT NOT NULL,
    author_id BIGINT NOT NULL,
    answer_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (answer_id) REFERENCES answers(id)
)