package org.miracle.service;

import org.miracle.dto.AnswerDTO;
import org.miracle.model.Answer;
import org.miracle.model.Question;
import org.springframework.data.domain.Page;

/**
 * Класс, отвечающий за получение, создание и удаление сущности {@link Answer}
 *
 * @author Mark Valiev
 * @since 21.09.2024
 */
public interface AnswerService {

    /**
     * Метод создает новый ответ на вопрос пользователя
     * @param dto - DTO ответа пользователя
     * @param questionId - id сущности вопроса пользователя
     */
    void create(AnswerDTO dto, Long questionId);

    /**
     * Метод возвращает все ответы на вопрос согласно заданным параметрам сортировки
     * @param question - вопрос пользователя
     * @param sortByReputation - данные сортировки
     * @return - возвращает отсортированный лист вопросов
     */
    Page<AnswerDTO> showAnswers(Question question, boolean sortByReputation, Integer offset, Integer limit);

    /**
     * Метод удаляет вопрос по его id
     * @param questionId - id сущности вопроса пользователя
     */
    void delete(Long questionId);

    /**
     * Метод, увеличивает репутацию ответа на вопрос
     * @param answerId - id сущности ответа на вопрос
     */
    void addReputation(Long answerId);

}
