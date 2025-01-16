package org.miracle.repository;


import org.miracle.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий сущности {@link Question}
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM questions q ORDER BY q.reputation DESC")
    Page<Question> findAllByReputation(Pageable pageable);

    @Query("SELECT q FROM questions q ORDER BY q.createdAt")
    Page<Question> findAllByCreatedAt(Pageable pageable);

}