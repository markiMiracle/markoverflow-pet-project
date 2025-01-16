package org.miracle.repository;

import org.miracle.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности {@link Comment}
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
