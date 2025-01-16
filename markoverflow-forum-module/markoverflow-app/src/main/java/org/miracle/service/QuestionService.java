package org.miracle.service;

import org.miracle.dto.QuestionDTO;
import org.miracle.model.Question;
import org.springframework.data.domain.Page;

/**
 * Класс, отвечающий за получение, создание удаление сущности {@link Question}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
public interface QuestionService {

    /**
     * Метод, создающий новый вопрос пользователя
     * @param dto - DTO вопроса пользователя
     */
    void create(QuestionDTO dto);

    /**
     * Метод, возвращающий данные вопроса пользователя
     * @param questionId - id сущности вопроса
     * @param sortByReputation - данные сортировки для ответов на сущность вопроса
     * @return возвращает данные вопроса пользователя
     */
    QuestionDTO show(Long questionId, boolean sortByReputation, Integer offset, Integer limit);

    /**
     * Метод, возвращающий все вопросы по заданным параметрам сортировки
     * @param sortByReputation - параметры сортировки
     * @return возвращает список вопросов
     */
    Page<QuestionDTO> showAll(boolean sortByReputation, Integer offset, Integer limit);

    /**
     * Удаляет вопрос пользователя по его id
     * @param questionId - id сущности вопроса
     */
    void delete(Long questionId);

    /**
     * Метод, добавляющий репутацию сущности вопроса
     * @param questionId - id сущности вопроса
     */
    void addReputation(Long questionId);

}
