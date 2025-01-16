--liquibase formatted sql

-- changeset Mark:create-answers

CREATE TABLE IF NOT EXISTS answers(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    content TEXT NOT NULL,
    created_at DATE NOT NULL,
    reputation INTEGER,
    author_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
)