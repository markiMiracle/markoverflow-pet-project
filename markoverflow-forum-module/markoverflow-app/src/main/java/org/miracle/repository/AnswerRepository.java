package org.miracle.repository;

import org.miracle.model.Answer;
import org.miracle.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий сущности {@link Answer}
 * @author Mark Valiev
 * @since 18.09.2024
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT a FROM answers a WHERE a.question = :question ORDER BY a.reputation DESC")
    Page<Answer> findAllByReputation(@Param("question") Question question, Pageable pageable);

    @Query("SELECT a FROM answers a WHERE a.question = :question ORDER BY a.createdAt")
    Page<Answer> findAllByCreatedAt(@Param("question") Question question, Pageable pageable);

}

